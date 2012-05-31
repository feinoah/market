package cn.com.carit.market.dao.impl.permission;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.permission.BaseModuleDao;
@Repository
public class BaseModuleDaoImpl extends BaseDaoImpl  implements
		BaseModuleDao {
		private final RowMapper<BaseModule> rowMapper=new RowMapper<BaseModule>() {
			
			@Override
			public BaseModule mapRow(ResultSet rs, int index) throws SQLException {
				BaseModule baseModule=new BaseModule();
				baseModule.setId(rs.getInt("id"));
				baseModule.setModuleName(rs.getString("module_name"));
				baseModule.setModuleUrl(rs.getString("module_url"));
				baseModule.setParentId(rs.getInt("parent_id"));
				baseModule.setExpanded(rs.getBoolean("expanded"));
				baseModule.setIconCss(rs.getString("iconCss"));
				baseModule.setDisplay(rs.getBoolean("display"));
				baseModule.setLevel(rs.getInt("level"));
				baseModule.setInformation(rs.getString("information"));
				baseModule.setDisplayIndex(rs.getShort("display_index"));
				baseModule.setCreateTime(rs.getTimestamp("create_time"));
				baseModule.setUpdateTime(rs.getTimestamp("update_time"));
				return baseModule;
			}
		};

		@Override
		public int add(BaseModule baseModule) {
			String sql = "insert into t_base_module ("
					+"module_name"
					+", module_url"
					+", parent_id"
					+", expanded"
//					+", iconCss"
					+", display"
					+", display_index"
					+", level"
					+", information"
					+", create_time"
					+", update_time"
					+") values ("
					+"?"
					+", ?"
					+", ?"
					+", ?"
//					+", iconCss"
					+", ?"
					+", ?"
					+", ?"
					+", ?"
					+", now()"
					+", now()"
					+")";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql
					,baseModule.getModuleName()
					,baseModule.getModuleUrl()
					,baseModule.getParentId()
					,baseModule.getExpanded()
//					,baseModule.getIconCss()
					,baseModule.getDisplay()
					,baseModule.getDisplayIndex()
					,baseModule.getLevel()
					,baseModule.getInformation()
			);
		}

		@Override
		public int delete(int id) {
			String sql="delete from t_base_module where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, id);
		}

		@Override
		public int update(BaseModule baseModule) {
			StringBuilder sql=new StringBuilder("update t_base_module set update_time=now()");
			List<Object> val=new ArrayList<Object>();
			if (StringUtils.hasText(baseModule.getModuleName())) {
				sql.append(", module_name=?");
				val.add(baseModule.getModuleName());
			}
			if (StringUtils.hasText(baseModule.getModuleUrl())) {
				sql.append(",module_url=?");
				val.add(baseModule.getModuleUrl());
			}
			if (baseModule.getParentId()!=null) {
				sql.append(",parent_id=?");
				val.add(baseModule.getParentId());
			}
			if (baseModule.getExpanded()!=null) {
				sql.append(",expanded=?");
				val.add(baseModule.getExpanded());
			}
			if (baseModule.getDisplayIndex()!=null) {
				sql.append(",display_index=?");
				val.add(baseModule.getDisplayIndex());
			}
			if (baseModule.getDisplay()!=null) {
				sql.append(",display=?");
				val.add(baseModule.getDisplay());
			}
			if (StringUtils.hasText(baseModule.getIconCss())) {
				sql.append(",icon_css=?");
				val.add(baseModule.getIconCss());
			}
			if (StringUtils.hasText(baseModule.getInformation())) {
				sql.append(",information=?");
				val.add(baseModule.getInformation());
			}
			if (baseModule.getLevel()!=null) {
				sql.append(",level=?");
				val.add(baseModule.getLevel());
			}
			sql.append(" where id=?");
			val.add(baseModule.getId());
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql.toString(), val.toArray());
		}

		@Override
		public BaseModule queryById(int id) {
			String sql="select * from t_base_module where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql, id, rowMapper);
		}

		@Override
		public List<BaseModule> query() {
			String sql="select * from t_base_module";
			log.debug(String.format("\n%1$s\n", sql));
			return this.jdbcTemplate.query(sql, rowMapper);
		}

		@Override
		public List<BaseModule> queryByExemple(BaseModule baseModule) {
			StringBuilder sql=new StringBuilder("select * from t_base_module where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			sql.append(buildWhere(args, argTypes, baseModule));
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql.toString(), args, argTypes, rowMapper);
		}

		@Override
		public JsonPage<BaseModule> queryByExemple(BaseModule baseModule, DataGridModel dgm) {
			JsonPage<BaseModule> jsonPage=new JsonPage<BaseModule>(dgm.getPage(), dgm.getRows());
			StringBuilder sql=new StringBuilder("select * from t_base_module where id>?");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			args.add(BaseModule.MAX_SYSTEM_MODULE_ID);//最大的权限管理模块Id
			argTypes.add(Types.INTEGER);
			String whereSql=buildWhere(args, argTypes, baseModule);
			sql.append(whereSql);
			String countSql="select count(1) from t_base_module where id>? "+whereSql;
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
				List<Integer> argTypes, BaseModule baseModule){
			StringBuilder sql=new StringBuilder();
			if (StringUtils.hasText(baseModule.getModuleName())) {
				sql.append(" and module_name like CONCAT('%',?,'%')");
				args.add(baseModule.getModuleName());
				argTypes.add(Types.VARCHAR);
			}
			if (StringUtils.hasText(baseModule.getModuleUrl())) {
				sql.append(" and module_url like CONCAT('%',?,'%')");
				args.add(baseModule.getModuleUrl());
				argTypes.add(Types.VARCHAR);
			}
			if (baseModule.getParentId()!=null) {
				sql.append(" and parent_id=?");
				args.add(baseModule.getParentId());
				argTypes.add(Types.INTEGER);
			}
			if (baseModule.getExpanded()!=null) {
				sql.append(" and expanded=?");
				args.add(baseModule.getExpanded());
				argTypes.add(Types.INTEGER);
			}
			if (baseModule.getDisplayIndex()!=null) {
				sql.append(" and display_index=?");
				args.add(baseModule.getDisplayIndex());
				argTypes.add(Types.INTEGER);
			}
			if (baseModule.getDisplay()!=null) {
				sql.append(" and display=?");
				args.add(baseModule.getDisplay());
				argTypes.add(Types.INTEGER);
			}
			/*if (StringUtils.hasText(baseModule.getIconCss())) {
				sql.append(" and icon_css like CONCAT('%',?,'%')");
				args.add(baseModule.getIconCss());
				argTypes.add(Types.VARCHAR);
			}*/
			if (baseModule.getLevel()!=null) {
				sql.append(" and level>=?");
				args.add(baseModule.getLevel());
				argTypes.add(Types.INTEGER);
			}
			if (StringUtils.hasText(baseModule.getInformation())) {
				sql.append(" and information like CONCAT('%',?,'%')");
				args.add(baseModule.getInformation());
				argTypes.add(Types.VARCHAR);
			}
			return sql.toString();
		}

		@Override
		public List<BaseModule> queryByUserId(int userId) {
			String sql="select m.* from t_base_module m left join t_base_role_module b"
					+ " on m.id=b.module_id where exists "
					+ "(select r.role_id from t_base_user_role r left join t_base_user u on u.id=r.user_id "
					+ "where r.role_id=b.role_id and r.user_id=?) group by m.id order by level, display_index";
			log.debug(String.format("\n%1$s\n", sql));
			log.debug("userId:"+String.format("\n%1$s\n", userId));
			return jdbcTemplate.query(sql, new Object[]{userId}, new int[]{Types.INTEGER}, rowMapper);
		}

		@Override
		public List<BaseModule> queryByRoleId(int roleId) {
			String sql="select m.* from t_base_module m left join t_base_role_module b"
					+ " on m.id=b.module_id where b.role_id=? order by level, display_index ";
			log.debug(String.format("\n%1$s\n", sql));
			log.debug("userId:"+String.format("\n%1$s\n", roleId));
			return jdbcTemplate.query(sql, new Object[]{roleId}, new int[]{Types.INTEGER}, rowMapper);
		}

}
