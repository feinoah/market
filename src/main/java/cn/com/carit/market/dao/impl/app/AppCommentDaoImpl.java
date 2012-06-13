package cn.com.carit.market.dao.impl.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppCommentDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppCommentDaoImpl Auto generated Code
 */
@Repository
public class AppCommentDaoImpl extends BaseDaoImpl implements AppCommentDao {
	private final RowMapper<AppComment> rowMapper = new RowMapper<AppComment>() {

		@Override
		public AppComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			AppComment appComment = new AppComment();
			appComment.setId(rs.getInt("id"));
			appComment.setAppId(rs.getInt("app_id"));
			appComment.setUserId(rs.getInt("user_id"));
			appComment.setGrade(rs.getInt("grade"));
			appComment.setComment(rs.getString("comment"));
			appComment.setStatus(rs.getInt("status"));
			appComment.setCreateTime(rs.getTimestamp("create_time"));
			appComment.setUpdateTime(rs.getTimestamp("update_time"));
			appComment.setAppName(rs.getString("app_name"));
			appComment.setEnName(rs.getString("en_name"));
			appComment.setUserName(rs.getString("nick_name"));
			return appComment;
		}
	};

	@Override
	public int add(final AppComment appComment) {
		final String sql = "insert into t_app_comment (" + "	app_id"
				+ ", user_id" + ", grade" + ", comment" + ", create_time"
				+ ", update_time" + ") values (?, ?, ?, ?, now(), now())";
		log.debug(String.format("\n%1$s\n", sql));
		KeyHolder gkHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setInt(1, appComment.getAppId());
				ps.setInt(2, appComment.getUserId());
				ps.setInt(3, appComment.getGrade());
				ps.setString(4, appComment.getComment());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_app_comment where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int deleteByAppId(int appId) {
		String sql = "delete from t_app_comment where app_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, appId);
	}

	@Override
	public int update(AppComment appComment) {
		StringBuilder sql = new StringBuilder(
				"update t_app_comment set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (appComment.getAppId() != null) {
			sql.append(", app_id=?");
			args.add(appComment.getAppId());
		}
		if (appComment.getUserId() != null) {
			sql.append(", user_id=?");
			args.add(appComment.getUserId());
		}
		if (appComment.getGrade() != null) {
			sql.append(", grade=?");
			args.add(appComment.getGrade());
		}
		if (StringUtils.hasText(appComment.getComment())) {
			sql.append(", comment=?");
			args.add(appComment.getComment());
		}
		if (appComment.getStatus() != null) {
			sql.append(", status=?");
			args.add(appComment.getStatus());
		}
		if (appComment.getCreateTime() != null) {
			sql.append(", create_time=?");
			args.add(appComment.getCreateTime());
		}
		if (appComment.getUpdateTime() != null) {
			sql.append(", update_time=?");
			args.add(appComment.getUpdateTime());
		}
		sql.append(" where id=?");
		args.add(appComment.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public AppComment queryById(int id) {
		String sql = "select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public List<AppComment> query() {
		String sql="select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id";
		log.debug(String.format("\n%1$s\n", sql));
		return this.jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public List<AppComment> queryByExemple(AppComment appComment) {
		StringBuilder sql = new StringBuilder("select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, appComment));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<AppComment> queryByExemple(AppComment appComment, DataGridModel dgm) {
		JsonPage<AppComment> jsonPage = new JsonPage<AppComment>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder("select a.*,b.app_name, b.en_name, c.nick_name from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, appComment);
		sql.append(whereSql);
		String countSql="select count(1) from t_app_comment a left join t_application b on a.app_id=b.id left join t_account_info c on a.user_id=c.id where 1=1"+whereSql;
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow = queryForInt(countSql, args, argTypes);
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
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
			List<Integer> argTypes, AppComment appComment) {
		StringBuilder sql=new StringBuilder();
		if (appComment.getAppId() != null) {
			sql.append(" and a.app_id=?");
			args.add(appComment.getAppId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(appComment.getAppName())){
			sql.append(" and b.app_name like CONCAT('%',?,'%')");
			args.add(appComment.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if(StringUtils.hasText(appComment.getEnName())){
			sql.append(" and  b.en_name like CONCAT('%',?,'%')");
			args.add(appComment.getEnName());
			argTypes.add(12);// java.sql.Types type
		}
		if (appComment.getUserId() != null) {
			sql.append(" and a.user_id=?");
			args.add(appComment.getUserId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(appComment.getUserName())){
			sql.append(" and  c.nick_name like CONCAT('%',?,'%')");
			args.add(appComment.getUserName());
			argTypes.add(12);// java.sql.Types type
		}
		if (appComment.getGrade() != null) {
			sql.append(" and a.grade=?");
			args.add(appComment.getGrade());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(appComment.getComment())) {
			sql.append(" and a.comment like CONCAT('%',?,'%')");
			args.add(appComment.getComment());
			argTypes.add(12);// java.sql.Types type
		}
		if (appComment.getStatus() != null) {
			sql.append(" and a.status=?");
			args.add(appComment.getStatus());
			argTypes.add(4);// java.sql.Types type
		}
		if (appComment.getCreateTime() != null) {
			sql.append(" and a.create_time=?");
			args.add(appComment.getCreateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (appComment.getUpdateTime() != null) {
			sql.append(" and a.update_time=?");
			args.add(appComment.getUpdateTime());
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}
	
	private final RowMapper<PortalAppComment> portalRowMapper = new RowMapper<PortalAppComment>() {

		@Override
		public PortalAppComment mapRow(ResultSet rs, int rowNum) throws SQLException {
			PortalAppComment appComment = new PortalAppComment();
			appComment.setGrade(rs.getInt("grade"));
			appComment.setComment(rs.getString("comment"));
			appComment.setNickName(rs.getString("nick_name"));
			appComment.setUpdateTime(rs.getTimestamp("update_time"));
			return appComment;
		}
	};

	@Override
	public JsonPage<PortalAppComment> queryComment(int appId, DataGridModel dgm) {
		StringBuilder sql = new StringBuilder(
				"select a.grade, a.comment, a.update_time, b.nick_name from t_app_comment a left join t_account_info b on a.user_id=b.id where a.status=? and a.app_id=?");
		String countSql = "select count(1) from t_app_comment a left join t_account_info b on a.user_id=b.id where a.status=? and a.app_id=?";
		JsonPage<PortalAppComment> jsonPage = new JsonPage<PortalAppComment>(dgm.getPage(), dgm.getRows());
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		args.add(Constants.STATUS_VALID);
		argTypes.add(Types.INTEGER);
		args.add(appId);
		argTypes.add(Types.INTEGER);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		} else {
			sql.append(" order by a.update_time desc");
		}
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow = queryForInt(countSql, args, argTypes);
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		// 更新
		jsonPage.setTotal(totalRow);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, portalRowMapper));
		return jsonPage;
	}
	
	public double queryAvgGrade(int appId) {
		String sql="select avg(grade) from t_app_comment where app_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForObject(sql, Double.class, appId);
		} catch (Exception e) {
			log.warn("no comment of app["+appId+"]...");
			log.warn(e.getMessage());
		}
		return 0;
	}

	@Override
	public Map<String,Object> statComment(int appId) {
		String sql="select count(1) count, avg(grade) avg, sum(grade) sum from t_app_comment where app_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForMap(sql, appId);
		} catch (Exception e) {
			log.warn("no comment of app["+appId+"]...");
			log.warn(e.getMessage());
		}
		return null;
	}

	@Override
	public List<Map<String, Object>> statCommentGrade(int appId) {
		String sql="select round(grade/2) grade, count(1) total from t_app_comment where app_id=? group by round(grade/2)";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForList(sql, appId);
		} catch (Exception e) {
			log.warn("no comment of app["+appId+"]...");
			log.warn(e.getMessage());
		}
		return null;
	}

}
