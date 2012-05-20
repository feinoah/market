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

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppVersionFileDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppVersionFileDaoImpl
 * Auto generated Code
 */
@Repository
public class AppVersionFileDaoImpl extends BaseDaoImpl  implements
		AppVersionFileDao {
		private final RowMapper<AppVersionFile> rowMapper=new RowMapper<AppVersionFile>() {
			
			@Override
			public AppVersionFile mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppVersionFile appVersionFile=new AppVersionFile();
				appVersionFile.setId(rs.getInt("id"));
				appVersionFile.setAppId(rs.getInt("app_id"));
				appVersionFile.setVersion(rs.getString("version"));
				appVersionFile.setSize(rs.getString("size"));
				appVersionFile.setFilePath(rs.getString("file_path"));
				appVersionFile.setNewFeatures(rs.getString("new_features"));
				appVersionFile.setStatus(rs.getInt("status"));
				appVersionFile.setCreateTime(rs.getTimestamp("create_time"));
				appVersionFile.setUpdateTime(rs.getTimestamp("update_time"));
				return appVersionFile;
			}
		};

		@Override
		public int add(final AppVersionFile appVersionFile) {
			// TODO change values field name to ? and deal with date field
			final String sql = "insert into t_app_version_file ("
					+"	app_id"
					+", version"
					+", size"
					+", file_path"
					+", new_features"
//					+", status"
					+", create_time"
					+", update_time"
					+") values ("
					+"	?"
					+", ?"
					+", ?"
					+", ?"
					+", ?"
//					+", status"
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
					 ps.setInt(1, appVersionFile.getAppId());
					 ps.setString(2, appVersionFile.getVersion());
					 ps.setString(3, appVersionFile.getSize());
					 ps.setString(4, appVersionFile.getFilePath());
					 ps.setString(5, appVersionFile.getNewFeatures());
					 ps.setInt(6, appVersionFile.getStatus());
//					 ps.setDate(7, new Date(appVersionFile.getCreateTime().getTime()));
//					 ps.setDate(8, new Date(appVersionFile.getUpdateTime().getTime()));
					return ps;
				}
			},  gkHolder);
			return gkHolder.getKey().intValue();
		}

		@Override
		public int delete(int id) {
			String sql="delete from t_app_version_file where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, id);
		}

		@Override
		public int deleteByAppId(int appId) {
			String sql="delete from t_app_version_file where app_id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, appId);
		}

		@Override
		public int update(AppVersionFile appVersionFile) {
			StringBuilder sql=new StringBuilder("update t_app_version_file set update_time=now()");
			List<Object> args=new ArrayList<Object>();
			if (appVersionFile.getAppId()!=null) {
				sql.append(", app_id=?");
				args.add(appVersionFile.getAppId());
			}
			if (StringUtils.hasText(appVersionFile.getVersion())) {
				sql.append(", version=?");
				args.add(appVersionFile.getVersion());
			}
			if (StringUtils.hasText(appVersionFile.getSize())) {
				sql.append(", size=?");
				args.add(appVersionFile.getSize());
			}
			if (StringUtils.hasText(appVersionFile.getFilePath())) {
				sql.append(", file_path=?");
				args.add(appVersionFile.getFilePath());
			}
			if (StringUtils.hasText(appVersionFile.getNewFeatures())) {
				sql.append(", new_features=?");
				args.add(appVersionFile.getNewFeatures());
			}
			if (appVersionFile.getStatus()!=null) {
				sql.append(", status=?");
				args.add(appVersionFile.getStatus());
			}
			if (appVersionFile.getCreateTime()!=null) {
				sql.append(", create_time=?");
				args.add(appVersionFile.getCreateTime());
			}
			if (appVersionFile.getUpdateTime()!=null) {
				sql.append(", update_time=?");
				args.add(appVersionFile.getUpdateTime());
			}
			sql.append(" where id=?");
			args.add(appVersionFile.getId());
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql.toString(), args.toArray());
		}

		@Override
		public AppVersionFile queryById(int id) {
			String sql="select * from t_app_version_file where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql, id, rowMapper);
		}

		@Override
		public List<AppVersionFile> query() {
			return this.jdbcTemplate.query("select * from t_app_version_file", rowMapper);
		}

		@Override
		public List<AppVersionFile> queryByExemple(AppVersionFile appVersionFile) {
			StringBuilder sql=new StringBuilder("select * from t_app_version_file where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appVersionFile);
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql.toString(), args, argTypes, rowMapper);
		}

		@Override
		public JsonPage queryByExemple(AppVersionFile appVersionFile, DataGridModel dgm) {
			JsonPage jsonPage=new JsonPage(dgm.getPage(), dgm.getRows());
			StringBuilder sql=new StringBuilder("select * from t_app_version_file where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appVersionFile);
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
			int totalRow=getCount(appVersionFile);
			// 更新
			jsonPage.setTotal(totalRow);
			log.debug(String.format("\n%1$s\n", sql));
			jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
			return jsonPage;
		}

		@Override
		public int getCount(AppVersionFile appVersionFile) {
			StringBuilder sql=new StringBuilder("select count(1) from t_app_version_file where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appVersionFile);
			log.debug(String.format("\n%1$s\n", sql));
			return queryForInt(sql.toString(), args, argTypes);
		}
		
		private void buildWhere(StringBuilder sql, List<Object> args, 
				List<Integer> argTypes, AppVersionFile appVersionFile){
			if (appVersionFile.getAppId()!=null) {
				sql.append(" and app_id=?");
				args.add(appVersionFile.getAppId());
				argTypes.add(4);//java.sql.Types type
			}
			if (StringUtils.hasText(appVersionFile.getVersion())) {
				sql.append(" and version like CONCAT('%',?,'%')");
				args.add(appVersionFile.getVersion());
				argTypes.add(12);//java.sql.Types type
			}
			if (StringUtils.hasText(appVersionFile.getSize())) {
				sql.append(" and size like CONCAT('%',?,'%')");
				args.add(appVersionFile.getSize());
				argTypes.add(12);//java.sql.Types type
			}
			if (StringUtils.hasText(appVersionFile.getFilePath())) {
				sql.append(" and file_path like CONCAT('%',?,'%')");
				args.add(appVersionFile.getFilePath());
				argTypes.add(12);//java.sql.Types type
			}
			if (StringUtils.hasText(appVersionFile.getNewFeatures())) {
				sql.append(" and new_features like CONCAT('%',?,'%')");
				args.add(appVersionFile.getNewFeatures());
				argTypes.add(12);//java.sql.Types type
			}
			if (appVersionFile.getStatus()!=null) {
				sql.append(" and status=?");
				args.add(appVersionFile.getStatus());
				argTypes.add(4);//java.sql.Types type
			}
			if (appVersionFile.getCreateTime()!=null) {
				sql.append(" and create_time=?");
				args.add(appVersionFile.getCreateTime());
				argTypes.add(93);//java.sql.Types type
			}
			if (appVersionFile.getUpdateTime()!=null) {
				sql.append(" and update_time=?");
				args.add(appVersionFile.getUpdateTime());
				argTypes.add(93);//java.sql.Types type
			}
		}
}
