package cn.com.carit.market.dao.impl.app;
import java.sql.Connection;
import java.sql.Date;
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

import cn.com.carit.market.bean.app.AppAttachment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppAttachmentDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppAttachmentDaoImpl
 * Auto generated Code
 */
@Repository
public class AppAttachmentDaoImpl extends BaseDaoImpl  implements
		AppAttachmentDao {
		private final RowMapper<AppAttachment> rowMapper=new RowMapper<AppAttachment>() {
			
			@Override
			public AppAttachment mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppAttachment appAttachment=new AppAttachment();
				appAttachment.setAppId(rs.getInt("app_id"));
				appAttachment.setName(rs.getString("name"));
				appAttachment.setFilePath(rs.getString("file_path"));
				appAttachment.setStatus(rs.getInt("status"));
				appAttachment.setCreateTime(rs.getTimestamp("create_time"));
				appAttachment.setUpdateTime(rs.getTimestamp("update_time"));
				return appAttachment;
			}
		};

		@Override
		public int add(final AppAttachment appAttachment) {
			// TODO change values field name to ? and deal with date field
			final String sql = "insert into t_app_attachment ("
					+", app_id"
					+", name"
					+", file_path"
					+", status"
					+", create_time"
					+", update_time"
					+") values ("
					+", app_id"
					+", name"
					+", file_path"
					+", status"
					+", create_time"
					+", update_time"
					+")";
			log.debug(String.format("\n%1$s\n", sql));
			KeyHolder gkHolder = new GeneratedKeyHolder(); 
			jdbcTemplate.update(new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(Connection con)
						throws SQLException {
					 PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
					 ps.setInt(1, appAttachment.getAppId());
					 ps.setString(2, appAttachment.getName());
					 ps.setString(3, appAttachment.getFilePath());
					 ps.setInt(4, appAttachment.getStatus());
					 ps.setDate(5, new Date(appAttachment.getCreateTime().getTime()));
					 ps.setDate(6, new Date(appAttachment.getUpdateTime().getTime()));
					return ps;
				}
			},  gkHolder);
			return gkHolder.getKey().intValue();
		}

		@Override
		public int delete(int id) {
			String sql="delete from t_app_attachment where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, id);
		}

		@Override
		public int update(AppAttachment appAttachment) {
			StringBuilder sql=new StringBuilder("update t_app_attachment set update_time=now()");
			List<Object> args=new ArrayList<Object>();
			sql.append(" where id=?");
			if (appAttachment.getAppId()!=null) {
				sql.append(", app_id=?");
				args.add(appAttachment.getAppId());
			}
			if (StringUtils.hasText(appAttachment.getName())) {
				sql.append(", name=?");
				args.add(appAttachment.getName());
			}
			if (StringUtils.hasText(appAttachment.getFilePath())) {
				sql.append(", file_path=?");
				args.add(appAttachment.getFilePath());
			}
			if (appAttachment.getStatus()!=null) {
				sql.append(", status=?");
				args.add(appAttachment.getStatus());
			}
			if (appAttachment.getCreateTime()!=null) {
				sql.append(", create_time=?");
				args.add(appAttachment.getCreateTime());
			}
			if (appAttachment.getUpdateTime()!=null) {
				sql.append(", update_time=?");
				args.add(appAttachment.getUpdateTime());
			}
			args.add(appAttachment.getId());
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql.toString(), args.toArray());
		}

		@Override
		public AppAttachment queryById(int id) {
			String sql="select * from t_app_attachment where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql, id, rowMapper);
		}

		@Override
		public List<AppAttachment> query() {
			return this.jdbcTemplate.query("select * from t_app_attachment", rowMapper);
		}

		@Override
		public List<AppAttachment> queryByExemple(AppAttachment appAttachment) {
			StringBuilder sql=new StringBuilder("select * from t_app_attachment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appAttachment);
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql.toString(), args, argTypes, rowMapper);
		}

		@Override
		public JsonPage queryByExemple(AppAttachment appAttachment, DataGridModel dgm) {
			JsonPage jsonPage=new JsonPage(dgm.getPage(), dgm.getRows());
			StringBuilder sql=new StringBuilder("select * from t_app_attachment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appAttachment);
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
			int totalRow=getCount(appAttachment);
			// 更新
			jsonPage.setTotal(totalRow);
			log.debug(String.format("\n%1$s\n", sql));
			jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
			return jsonPage;
		}

		@Override
		public int getCount(AppAttachment appAttachment) {
			StringBuilder sql=new StringBuilder("select count(1) from t_app_attachment where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appAttachment);
			log.debug(String.format("\n%1$s\n", sql));
			return queryForInt(sql.toString(), args, argTypes);
		}
		
		private void buildWhere(StringBuilder sql, List<Object> args, 
				List<Integer> argTypes, AppAttachment appAttachment){
			if (appAttachment.getAppId()!=null) {
				sql.append(" and app_id=?");
				args.add(appAttachment.getAppId());
				argTypes.add(4);//java.sql.Types type
			}
			if (StringUtils.hasText(appAttachment.getName())) {
				sql.append(" and name like CONCAT('%',?,'%')");
				args.add(appAttachment.getName());
				argTypes.add(12);//java.sql.Types type
			}
			if (StringUtils.hasText(appAttachment.getFilePath())) {
				sql.append(" and file_path like CONCAT('%',?,'%')");
				args.add(appAttachment.getFilePath());
				argTypes.add(12);//java.sql.Types type
			}
			if (appAttachment.getStatus()!=null) {
				sql.append(" and status=?");
				args.add(appAttachment.getStatus());
				argTypes.add(4);//java.sql.Types type
			}
			if (appAttachment.getCreateTime()!=null) {
				sql.append(" and create_time=?");
				args.add(appAttachment.getCreateTime());
				argTypes.add(93);//java.sql.Types type
			}
			if (appAttachment.getUpdateTime()!=null) {
				sql.append(" and update_time=?");
				args.add(appAttachment.getUpdateTime());
				argTypes.add(93);//java.sql.Types type
			}
		}
}
