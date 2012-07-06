package cn.com.carit.market.dao.impl.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppDeveloper;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppDeveloperDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
@Repository
public class AppDeveloperDaoImpl extends BaseDaoImpl implements AppDeveloperDao {
	private final RowMapper<AppDeveloper> rowMapper = new RowMapper<AppDeveloper>() {
		@Override
		public AppDeveloper mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AppDeveloper developer=new AppDeveloper();
			developer.setId(rs.getInt("id"));
			developer.setName(rs.getString("name"));
			developer.setWebsite(rs.getString("website"));
			developer.setEmail(rs.getString("email"));
			developer.setRemark(rs.getString("remark"));
			developer.setStatus(rs.getInt("status"));
			developer.setCreateTime(rs.getTimestamp("create_time"));
			developer.setUpdateTime(rs.getTimestamp("update_time"));
			return developer;
		}
		
	};
	@Override
	public int add(AppDeveloper developer) {
		String sql = "insert into t_app_developer(name, website, email, remark"
				+", status, create_time, update_time) values(?, ?, ?, ?, ?, now(), now())";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, developer.getName(),
				developer.getWebsite(), developer.getEmail(),
				developer.getRemark(), developer.getStatus());
	}

	@Override
	public int update(AppDeveloper developer) {
		StringBuilder sql=new StringBuilder("update t_app_developer set update_time=now()");
		List<Object> val=new ArrayList<Object>();
		if (StringUtils.hasText(developer.getName())) {
			sql.append(", name=?");
			val.add(developer.getName());
		}
		if (StringUtils.hasText(developer.getWebsite())) {
			sql.append(", website=?");
			val.add(developer.getWebsite());
		}
		if (StringUtils.hasText(developer.getRemark())) {
			sql.append(", remark=?");
			val.add(developer.getRemark());
		}
		if (developer.getStatus()!=null) {
			sql.append(", status=?");
			val.add(developer.getStatus());
		}
		sql.append(" where id=?");
		val.add(developer.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), val.toArray());
	}

	@Override
	public int delete(int id) {
		String sql="delete from t_app_developer where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int batchDelete(String ids) {
		String sql="delete from t_app_developer where id in("+ids+")";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql);
	}

	@Override
	public AppDeveloper queryById(int id) {
		String sql="select * from t_app_developer where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public AppDeveloper queryByName(String name) {
		String sql="select * from t_app_developer where name=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, name, rowMapper);
	}

	@Override
	public List<AppDeveloper> queryByExemple(AppDeveloper developer) {
		StringBuilder sql=new StringBuilder("select * from t_app_developer where 1=1");
		List<Object> args=new ArrayList<Object>();
		List<Integer> argTypes=new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, developer));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<AppDeveloper> queryByExemple(AppDeveloper developer,
			DataGridModel dgm) {
		JsonPage<AppDeveloper> jsonPage=new JsonPage<AppDeveloper>(dgm.getPage(), dgm.getRows());
		StringBuilder sql=new StringBuilder("select * from t_app_developer where 1=1");
		StringBuilder countSql=new StringBuilder("select count(1) from t_app_developer where 1=1");
		List<Object> args=new ArrayList<Object>();
		List<Integer> argTypes=new ArrayList<Integer>();
		String where=buildWhere(args, argTypes, developer);
		sql.append(where);
		countSql.append(where);
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow=queryForInt(countSql.toString(), args, argTypes);
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder()) && StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ").append(StringUtil.splitFieldWords(
					dgm.getSort())).append(" ").append(dgm.getOrder());
			
		} else {
			sql.append(" order by update_time desc");
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
			List<Integer> argTypes, AppDeveloper developer) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(developer.getName())) {
			sql.append(" and name like CONCAT('%',?,'%')");
			args.add(developer.getName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(developer.getWebsite())) {
			sql.append(" and website like CONCAT('%',?,'%')");
			args.add(developer.getWebsite());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(developer.getEmail())) {
			sql.append(" and email like CONCAT('%',?,'%')");
			args.add(developer.getEmail());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(developer.getRemark())) {
			sql.append(" and remark like CONCAT('%',?,'%')");
			args.add(developer.getRemark());
			argTypes.add(Types.VARCHAR);
		}
		if (developer.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(developer.getStatus());
			argTypes.add(Types.INTEGER);
		}
		return sql.toString();
	}
	
	@Override
	public int checkExisted(String name) {
		String sql="select 1 from t_app_developer where name=?";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForInt(sql, name);
		} catch (Exception e) {
			log.warn("not exist record of this name["+name+"]");
			log.warn(e.getMessage());
		}
		return 0;
	}

}
