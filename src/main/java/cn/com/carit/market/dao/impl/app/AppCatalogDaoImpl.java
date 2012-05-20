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

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppCatalogDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppCatalogDaoImpl
 * Auto generated Code
 */
@Repository
public class AppCatalogDaoImpl extends BaseDaoImpl  implements
		AppCatalogDao {
		private final RowMapper<AppCatalog> rowMapper=new RowMapper<AppCatalog>() {
			
			@Override
			public AppCatalog mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppCatalog appCatalog=new AppCatalog();
				appCatalog.setId(rs.getInt("id"));
				appCatalog.setName(rs.getString("name"));
				appCatalog.setDescription(rs.getString("description"));
				appCatalog.setDisplayIndex(rs.getInt("display_index"));
				appCatalog.setStatus(rs.getByte("status"));
				appCatalog.setCreateTime(rs.getTimestamp("create_time"));
				appCatalog.setUpdateTime(rs.getTimestamp("update_time"));
				return appCatalog;
			}
		};

		@Override
		public int add(final AppCatalog appCatalog) {
			// TODO change values field name to ? and deal with date field
			final String sql = "insert into t_app_catalog ("
					+"	name"
					+", description"
					+", display_index"
					+", status"
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
					 ps.setString(1, appCatalog.getName());
					 ps.setString(2, appCatalog.getDescription());
					 ps.setInt(3, appCatalog.getDisplayIndex());
					 ps.setByte(4, appCatalog.getStatus());
//					 ps.setDate(5, new Date(appCatalog.getCreateTime().getTime()));
//					 ps.setDate(6, new Date(appCatalog.getUpdateTime().getTime()));
					return ps;
				}
			},  gkHolder);
			return gkHolder.getKey().intValue();
		}

		@Override
		public int delete(int id) {
			String sql="delete from t_app_catalog where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, id);
		}

		@Override
		public int update(AppCatalog appCatalog) {
			StringBuilder sql=new StringBuilder("update t_app_catalog set update_time=now()");
			List<Object> args=new ArrayList<Object>();
			if (StringUtils.hasText(appCatalog.getName())) {
				sql.append(", name=?");
				args.add(appCatalog.getName());
			}
			if (StringUtils.hasText(appCatalog.getDescription())) {
				sql.append(", description=?");
				args.add(appCatalog.getDescription());
			}
			if (appCatalog.getDisplayIndex()!=null) {
				sql.append(", display_index=?");
				args.add(appCatalog.getDisplayIndex());
			}
			if (appCatalog.getStatus()!=null) {
				sql.append(", status=?");
				args.add(appCatalog.getStatus());
			}
			if (appCatalog.getCreateTime()!=null) {
				sql.append(", create_time=?");
				args.add(appCatalog.getCreateTime());
			}
			if (appCatalog.getUpdateTime()!=null) {
				sql.append(", update_time=?");
				args.add(appCatalog.getUpdateTime());
			}
			sql.append(" where id=?");
			args.add(appCatalog.getId());
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql.toString(), args.toArray());
		}

		@Override
		public AppCatalog queryById(int id) {
			String sql="select * from t_app_catalog where id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql, id, rowMapper);
		}

		@Override
		public List<AppCatalog> query() {
			return this.jdbcTemplate.query("select * from t_app_catalog", rowMapper);
		}

		@Override
		public List<AppCatalog> queryByExemple(AppCatalog appCatalog) {
			StringBuilder sql=new StringBuilder("select * from t_app_catalog where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appCatalog);
			log.debug(String.format("\n%1$s\n", sql));
			return query(sql.toString(), args, argTypes, rowMapper);
		}

		@Override
		public JsonPage queryByExemple(AppCatalog appCatalog, DataGridModel dgm) {
			JsonPage jsonPage=new JsonPage(dgm.getPage(), dgm.getRows());
			StringBuilder sql=new StringBuilder("select * from t_app_catalog where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appCatalog);
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
			int totalRow=getCount(appCatalog);
			// 更新
			jsonPage.setTotal(totalRow);
			log.debug(String.format("\n%1$s\n", sql));
			jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
			return jsonPage;
		}

		@Override
		public int getCount(AppCatalog appCatalog) {
			StringBuilder sql=new StringBuilder("select count(1) from t_app_catalog where 1=1");
			List<Object> args=new ArrayList<Object>();
			List<Integer> argTypes=new ArrayList<Integer>();
			buildWhere(sql, args, argTypes, appCatalog);
			log.debug(String.format("\n%1$s\n", sql));
			return queryForInt(sql.toString(), args, argTypes);
		}
		
		private void buildWhere(StringBuilder sql, List<Object> args, 
				List<Integer> argTypes, AppCatalog appCatalog){
			if (StringUtils.hasText(appCatalog.getName())) {
				sql.append(" and name like CONCAT('%',?,'%')");
				args.add(appCatalog.getName());
				argTypes.add(12);//java.sql.Types type
			}
			if (StringUtils.hasText(appCatalog.getDescription())) {
				sql.append(" and description like CONCAT('%',?,'%')");
				args.add(appCatalog.getDescription());
				argTypes.add(12);//java.sql.Types type
			}
			if (appCatalog.getDisplayIndex()!=null) {
				sql.append(" and display_index=?");
				args.add(appCatalog.getDisplayIndex());
				argTypes.add(4);//java.sql.Types type
			}
			if (appCatalog.getStatus()!=null) {
				sql.append(" and status=?");
				args.add(appCatalog.getStatus());
				argTypes.add(-6);//java.sql.Types type
			}
			if (appCatalog.getCreateTime()!=null) {
				sql.append(" and create_time=?");
				args.add(appCatalog.getCreateTime());
				argTypes.add(93);//java.sql.Types type
			}
			if (appCatalog.getUpdateTime()!=null) {
				sql.append(" and update_time=?");
				args.add(appCatalog.getUpdateTime());
				argTypes.add(93);//java.sql.Types type
			}
		}
}
