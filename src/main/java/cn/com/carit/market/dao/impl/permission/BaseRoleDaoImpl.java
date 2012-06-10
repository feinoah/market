package cn.com.carit.market.dao.impl.permission;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.permission.BaseRoleDao;
@Repository
public class BaseRoleDaoImpl extends BaseDaoImpl  implements
		BaseRoleDao {
	private final RowMapper<BaseRole> rowMapper=new RowMapper<BaseRole>() {
		
		@Override
		public BaseRole mapRow(ResultSet rs, int arg1) throws SQLException {
			BaseRole baseRole=new BaseRole();
			baseRole.setId(rs.getInt("id"));
			baseRole.setRoleName(rs.getString("role_name"));
			baseRole.setRoleDesc(rs.getString("role_desc"));
			baseRole.setCreateTime(rs.getTimestamp("create_time"));
			baseRole.setUpdateTime(rs.getTimestamp("update_time"));
			return baseRole;
		}
	};

	@Override
	public int add(final BaseRole baseRole) {
		final String sql = "insert into t_base_role ("
				+" role_name"
				+", role_desc"
				+", create_time"
				+", update_time"
				+") values ("
				+"?"
				+", ?"
				+", now()"
				+", now()"
				+")";
		log.debug(String.format("\n%1$s\n", sql));
		KeyHolder gkHolder = new GeneratedKeyHolder(); 
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				 PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				 ps.setString(1, baseRole.getRoleName());
				 ps.setString(2, baseRole.getRoleDesc());
				return ps;
			}
		},  gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql="delete from t_base_role where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int update(BaseRole baseRole) {
		StringBuilder sql=new StringBuilder("update t_base_role set update_time=now()");
		List<Object> val=new ArrayList<Object>();
		if (StringUtils.hasText(baseRole.getRoleName())) {
			sql.append(", role_name=?");
			val.add(baseRole.getRoleName());
		}
		if (StringUtils.hasText(baseRole.getRoleDesc())) {
			sql.append(", role_desc=?");
			val.add(baseRole.getRoleDesc());
		}
		sql.append(" where id=?");
		val.add(baseRole.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), val.toArray());
	}

	@Override
	public BaseRole queryById(int id) {
		String sql="select * from t_base_role where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public List<BaseRole> queryByUserId(int userId) {
		String sql="select a.* from t_base_role a left join t_base_user_role b on a.id=b.role_id where b.user_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.query(sql, new Object[]{userId},
				new int []{Types.INTEGER}, rowMapper);
	}

	@Override
	public List<BaseRole> query() {
		return this.jdbcTemplate.query("select * from t_base_role", rowMapper);
	}

	@Override
	public List<BaseRole> queryByExemple(BaseRole baseRole) {
		StringBuilder sql=new StringBuilder("select * from t_base_role where 1=1");
		List<Object> args=new ArrayList<Object>();
		List<Integer> argTypes=new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, baseRole));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<BaseRole> queryByExemple(BaseRole baseRole, DataGridModel dgm) {
		JsonPage<BaseRole> jsonPage=new JsonPage<BaseRole>(dgm.getPage(), dgm.getRows());
		StringBuilder sql=new StringBuilder("select * from t_base_role where 1=1");
		List<Object> args=new ArrayList<Object>();
		List<Integer> argTypes=new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, baseRole);
		sql.append(whereSql);
		String countSql="select count(1) from t_base_role where 1=1"+whereSql;
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow=queryForInt(countSql, args, argTypes);
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder()) && StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ").append(StringUtil.splitFieldWords(
					dgm.getSort())).append(" ").append(dgm.getOrder());
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	private String buildWhere(List<Object> args, 
			List<Integer> argTypes, BaseRole baseRole){
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(baseRole.getRoleName())) {
			sql.append(" and role_name like CONCAT('%',?,'%')");
			args.add(baseRole.getRoleName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(baseRole.getRoleDesc())) {
			sql.append(" and role_desc like CONCAT('%',?,'%')");
			args.add(baseRole.getRoleDesc());
			argTypes.add(Types.VARCHAR);
		}
		return sql.toString();
	}
	@Override
	public List<BaseModule> queryModulesByRoleId(int roleId) {
		String sql="select a.* from t_base_module a left join t_base_role_module b on a.id=b.module_id where b.role_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		List<BaseModule> list=jdbcTemplate.queryForList(sql, new Object[]{roleId},
				new int []{Types.INTEGER}, BaseModule.class);
		return list;
	}

	@Override
	public int checkRoleName(String roleName) {
		String sql="select 1 from t_base_module where role_name=?";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForInt(sql, roleName);
		} catch (Exception e) {
			log.warn("not exist record of this roleName["+roleName+"]");
		}
		return 0;
	}
	
}
