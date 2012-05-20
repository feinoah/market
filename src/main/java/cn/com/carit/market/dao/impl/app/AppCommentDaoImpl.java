package cn.com.carit.market.dao.impl.app;
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

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppCommentDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppCommentDaoImpl
 * Auto generated Code
 */
@Repository
public class AppCommentDaoImpl extends BaseDaoImpl  implements
		AppCommentDao {
		private final RowMapper<AppComment> rowMapper=new RowMapper<AppComment>() {
			
			@Override
			public AppComment mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppComment appComment=new AppComment();
				appComment.setId(rs.getInt("id"));
				appComment.setAppId(rs.getInt("app_id"));
				appComment.setUserId(rs.getInt("user_id"));
				appComment.setGrade(rs.getInt("grade"));
				appComment.setComment(rs.getString("comment"));
				appComment.setStatus(rs.getInt("status"));
				appComment.setCreateTime(rs.getTimestamp("create_time"));
				appComment.setUpdateTime(rs.getTimestamp("update_time"));
				return appComment;
			}
		};

		@Override
		public int add(final AppComment appComment) {
			final String sql = "insert into t_app_comment ("
					+"	app_id"
					+", user_id"
					+", grade"
					+", comment"
					+", create_time"
					+", update_time"
					+") values (?, ?, ?, ?, now(), now())";
			log.debug(String.format("\n%1$s\n", sql));
			KeyHolder gkHolder = new GeneratedKeyHolder(); 
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					 PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					 ps.setInt(1, appComment.getAppId());
					 ps.setInt(2, appComment.getUserId());
					 ps.setInt(3, appComment.getGrade());
					 ps.setString(4, appComment.getComment());
					 ps.setInt(5, appComment.getStatus());
					return ps;
				}
			},  gkHolder);
			return gkHolder.getKey().intValue();
		}

		@Override
		public int delete(int id) {
			String sql="delete from t_app_comment where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, id);
		}
		
		@Override
		public int deleteByAppId(int appId) {
			String sql="delete from t_app_comment where app_id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, appId);
		}

		@Override
		public int update(AppComment appComment) {
			StringBuilder sql=new StringBuilder("update t_app_comment set update_time=now()");
			List<Object> args=new ArrayList<Object>();
			if (appComment.getAppId()!=null) {
				sql.append(", app_id=?");
				args.add(appComment.getAppId());
			}
			if (appComment.getUserId()!=null) {
				sql.append(", user_id=?");
				args.add(appComment.getUserId());
			}
			if (appComment.getGrade()!=null) {
				sql.append(", grade=?");
				args.add(appComment.getGrade());
			}
			if (StringUtils.hasText(appComment.getComment())) {
				sql.append(", comment=?");
				args.add(appComment.getComment());
			}
			if (appComment.getStatus()!=null) {
				sql.append(", status=?");
				args.add(appComment.getStatus());
			}
			if (appComment.getCreateTime()!=null) {
				sql.append(", create_time=?");
				args.add(appComment.getCreateTime());
			}
			if (appComment.getUpdateTime()!=null) {
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
			String sql="select * from t_app_comment where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql, id, rowMapper);
		}

		@Override
		public List<AppComment> query() {
			return this.jdbcTemplate.query("select * from t_app_comment", rowMapper);
		}

		@Override
		public List<AppComment> queryByExemple(AppComment appComment) {
			StringBuilder sql=new StringBuilder("select * from t_app_comment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appComment);
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql.toString(), args, argTypes, rowMapper);
		}

		@Override
		public JsonPage queryByExemple(AppComment appComment, DataGridModel dgm) {
			JsonPage jsonPage=new JsonPage(dgm.getPage(), dgm.getRows());
			StringBuilder sql=new StringBuilder("select * from t_app_comment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appComment);
			// 排序
			if (StringUtils.hasText(dgm.getOrder()) && StringUtils.hasText(dgm.getSort())) {
				sql.append(" order by ").append(StringUtil.splitFieldWords(
						dgm.getSort())).append(" ").append(dgm.getOrder());
			}
			sql.append(" limit ?, ?");
			args.add(jsonPage.getStartRow());
			args.add(jsonPage.getEndRow());
			argTypes.add(Types.INTEGER);
			argTypes.add(Types.INTEGER);
			int totalRow=getCount(appComment);
			// 更新
			jsonPage.setTotal(totalRow);
			log.debug(String.format("\n%1$s\n", sql));
			jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
			return jsonPage;
		}

		@Override
		public int getCount(AppComment appComment) {
			StringBuilder sql=new StringBuilder("select count(1) from t_app_comment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appComment);
			log.debug(String.format("\n%1$s\n", sql));
			return queryForInt(sql.toString(), args, argTypes);
		}
		
		private void buildWhere(StringBuilder sql, List<Object> args, 
				List<Integer> argTypes, AppComment appComment){
			if (appComment.getAppId()!=null) {
				sql.append(" and app_id=?");
				args.add(appComment.getAppId());
				argTypes.add(4);//java.sql.Types type
			}
			if (appComment.getUserId()!=null) {
				sql.append(" and user_id=?");
				args.add(appComment.getUserId());
				argTypes.add(4);//java.sql.Types type
			}
			if (appComment.getGrade()!=null) {
				sql.append(" and grade=?");
				args.add(appComment.getGrade());
				argTypes.add(4);//java.sql.Types type
			}
			if (StringUtils.hasText(appComment.getComment())) {
				sql.append(" and comment like CONCAT('%',?,'%')");
				args.add(appComment.getComment());
				argTypes.add(12);//java.sql.Types type
			}
			if (appComment.getStatus()!=null) {
				sql.append(" and status=?");
				args.add(appComment.getStatus());
				argTypes.add(4);//java.sql.Types type
			}
			if (appComment.getCreateTime()!=null) {
				sql.append(" and create_time=?");
				args.add(appComment.getCreateTime());
				argTypes.add(93);//java.sql.Types type
			}
			if (appComment.getUpdateTime()!=null) {
				sql.append(" and update_time=?");
				args.add(appComment.getUpdateTime());
				argTypes.add(93);//java.sql.Types type
			}
		}
}
