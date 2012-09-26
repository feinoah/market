package cn.com.carit.market.dao.impl.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.app.UpdateNotice;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * ApplicationDaoImpl Auto generated Code
 */
@Repository
public class ApplicationDaoImpl extends BaseDaoImpl implements ApplicationDao {
	private final RowMapper<Application> rowMapper = new RowMapper<Application>() {

		@Override
		public Application mapRow(ResultSet rs, int rowNum) throws SQLException {
			Application application = new Application();
			application.setId(rs.getInt("id"));
			application.setAppName(rs.getString("app_name"));
			application.setEnName(rs.getString("en_name"));
			application.setVersion(rs.getString("version"));
			application.setIcon(rs.getString("icon"));
			application.setCatalogId(rs.getInt("catalog_id"));
			application.setSize(rs.getString("size"));
			application.setAppFilePath(rs.getString("app_file_path"));
			application.setPlatform(rs.getString("platform"));
			application.setSupportLanguages(rs.getInt("support_languages"));
			application.setPrice(rs.getDouble("price"));
			application.setDownCount(rs.getInt("down_count"));
			application.setAppLevel(rs.getInt("app_level"));
			application.setDescription(rs.getString("description"));
			application.setEnDescription(rs.getString("en_description"));
			application.setPermissionDesc(rs.getString("permission_desc"));
			application.setEnPermissionDesc(rs.getString("en_permission_desc"));
			application.setFeatures(rs.getString("features"));
			application.setEnFeatures(rs.getString("en_features"));
			application.setImages(rs.getString("images"));
			application.setStatus(rs.getInt("status"));
			application.setCreateTime(rs.getTimestamp("create_time"));
			application.setUpdateTime(rs.getTimestamp("update_time"));
			application.setBigIcon(rs.getString("big_icon"));
			application.setDeveloper(rs.getInt("developer"));
			application.setMainPic(rs.getString("main_pic"));
			return application;
		}
	};
	
	private final RowMapper<PortalApplication> portalRowMapper = new RowMapper<PortalApplication>() {

		@Override
		public PortalApplication mapRow(ResultSet rs, int rowNum) throws SQLException {
			PortalApplication application = new PortalApplication();
			application.setId(rs.getInt("id"));
			application.setAppName(rs.getString("app_name"));
			application.setVersion(rs.getString("version"));
			application.setIcon(rs.getString("icon"));
			application.setCatalogName(rs.getString("catalog_name"));
			application.setSize(rs.getString("size"));
			application.setAppFilePath(rs.getString("app_file_path"));
			application.setPlatform(rs.getString("platform"));
			application.setSupportLanguages(rs.getString("support_languages"));
			application.setPrice(rs.getDouble("price"));
			application.setDownCount(rs.getInt("down_count"));
			application.setAppLevel(rs.getInt("app_level"));
			application.setDescription(rs.getString("description"));
			application.setPermissionDesc(rs.getString("permission_desc"));
			application.setFeatures(rs.getString("features"));
			application.setImages(rs.getString("images"));
			application.setBigIcon(rs.getString("big_icon"));
			application.setDeveloper(rs.getString("developer"));
			application.setDeveloperWebsite(rs.getString("website"));
			application.setDeveloperEmail(rs.getString("email"));
			application.setUpdateTime(rs.getTimestamp("update_time"));
			application.setMainPic(rs.getString("main_pic"));
			return application;
		}
	};

	@Override
	public int add(final Application application) {
		final String sql = "insert into t_application (app_name"
				+ ", en_name, version, icon, big_icon, developer, catalog_id"
				+ ", size, app_file_path, platform"
				+ ", support_languages, price"
				+ ", description , permission_desc, en_description , en_permission_desc"
				+ ", images, status, create_time, update_time, features, en_features,main_pic"
				+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now(),?,?,?)";
		log.debug(String.format("\n%1$s\n", sql));
		KeyHolder gkHolder = new GeneratedKeyHolder(); 
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps=con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
				int i=1;
				ps.setString(i++, application.getAppName());
				ps.setString(i++, application.getEnName());
				ps.setString(i++, application.getVersion());
				ps.setString(i++, application.getIcon());
				ps.setString(i++, application.getBigIcon());
				ps.setInt(i++, application.getDeveloper());
				ps.setInt(i++, application.getCatalogId());
				ps.setString(i++, application.getSize()); 
				ps.setString(i++, application.getAppFilePath());
				ps.setString(i++, application.getPlatform());
				ps.setInt(i++, application.getSupportLanguages());
				ps.setDouble(i++, application.getPrice()==null?0:application.getPrice()); 
				ps.setString(i++, application.getDescription());
				ps.setString(i++, application.getPermissionDesc());
				ps.setString(i++, application.getEnDescription());
				ps.setString(i++, application.getEnPermissionDesc()); 
				ps.setString(i++, application.getImages());
				// 重置状态值
				if(application.getStatus().intValue()>Constants.STATUS_INVALID){
					application.setStatus(Constants.STATUS_VALID|application.getStatus());
				}
				ps.setInt(i++, application.getStatus());
				ps.setString(i++, application.getFeatures());
				ps.setString(i++, application.getEnFeatures());
				ps.setString(i++, application.getMainPic());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_application where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int update(Application application) {
		StringBuilder sql = new StringBuilder(
				"update t_application set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(application.getAppName())) {
			sql.append(", app_name=?");
			args.add(application.getAppName());
		}
		if (StringUtils.hasText(application.getEnName())) {
			sql.append(", en_name=?");
			args.add(application.getEnName());
		}
		if (StringUtils.hasText(application.getVersion())) {
			sql.append(", version=?");
			args.add(application.getVersion());
		}
		if (StringUtils.hasText(application.getIcon())) {
			sql.append(", icon=?");
			args.add(application.getIcon());
		}
		if (StringUtils.hasText(application.getBigIcon())) {
			sql.append(", big_icon=?");
			args.add(application.getBigIcon());
		}
		if (application.getDeveloper()!=null) {
			sql.append(", developer=?");
			args.add(application.getDeveloper());
		}
		if (application.getCatalogId() != null) {
			sql.append(", catalog_id=?");
			args.add(application.getCatalogId());
		}
		if (StringUtils.hasText(application.getSize())) {
			sql.append(", size=?");
			args.add(application.getSize());
		}
		if (StringUtils.hasText(application.getAppFilePath())) {
			sql.append(", app_file_path=?");
			args.add(application.getAppFilePath());
		}
		if (StringUtils.hasText(application.getPlatform())) {
			sql.append(", platform=?");
			args.add(application.getPlatform());
		}
		if (application.getSupportLanguages() != null) {
			sql.append(", support_languages=?");
			args.add(application.getSupportLanguages());
		}
		if (application.getPrice() != null) {
			sql.append(", price=?");
			args.add(application.getPrice());
		}
		if (application.getDownCount() != null) {
			sql.append(", down_count=?");
			args.add(application.getDownCount());
		}
		if (application.getAppLevel() != null) {
			sql.append(", app_level=?");
			args.add(application.getAppLevel());
		}
		if (StringUtils.hasText(application.getDescription())) {
			sql.append(", description=?");
			args.add(application.getDescription());
		}
		if (StringUtils.hasText(application.getEnDescription())) {
			sql.append(", en_description=?");
			args.add(application.getEnDescription());
		}
		if (StringUtils.hasText(application.getPermissionDesc())) {
			sql.append(", permission_desc=?");
			args.add(application.getPermissionDesc());
		}
		if (StringUtils.hasText(application.getEnPermissionDesc())) {
			sql.append(", en_permission_desc=?");
			args.add(application.getEnPermissionDesc());
		}
		if (application.getStatus() != null) {
			if(application.getStatus().intValue()==Constants.STATUS_INVALID){
				sql.append(", status=?");
			} else {
				sql.append(", status=(status|?)");
			}
			args.add(application.getStatus());
		}
		if (StringUtils.hasText(application.getImages())) {
			sql.append(", images=?");
			args.add(application.getImages());
		}
		if (StringUtils.hasText(application.getFeatures())) {
			sql.append(", features=?");
			args.add(application.getFeatures());
		}
		if (StringUtils.hasText(application.getFeatures())) {
			sql.append(", en_features=?");
			args.add(application.getEnFeatures());
		}
		if (StringUtils.hasText(application.getMainPic())) {
			sql.append(", main_pic=?");
			args.add(application.getMainPic());
		}
		sql.append(" where id=?");
		args.add(application.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}
	
	@Override
	public int updateById(int id) {
		String updateSql="update t_application a set app_file_path=(select file_path from t_app_version_file where id =(select max(id) from t_app_version_file where app_id=?)) where id=?";
		log.debug(String.format("\n%1$s\n", updateSql));
		return jdbcTemplate.update(updateSql, id, id);
	}

	@Override
	public Application queryById(int id) {
		String sql = "select * from t_application where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public List<Application> query() {
		String sql="select * from t_application";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public List<Application> queryByExemple(Application application) {
		StringBuilder sql = new StringBuilder(
				"select * from t_application where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, application));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<Application> queryByExemple(Application application, DataGridModel dgm) {
		JsonPage<Application> jsonPage = new JsonPage<Application>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_application where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, application);
		sql.append(whereSql);
		String countSql="select count(1) from t_application where 1=1"+whereSql;
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow = queryForInt(countSql, args, argTypes);
		// 更新
		jsonPage.setTotal(totalRow);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder()).append(", update_time desc");
		} else {
			sql.append(" order by update_time desc");
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		log.debug(String.format("\n%1$s\n", sql+", "+jsonPage.getStartRow())+", "+jsonPage.getPageSize());
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	private String buildWhere(List<Object> args,
			List<Integer> argTypes, Application application) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(application.getAppName())) {
			sql.append(" and app_name like CONCAT('%',?,'%')");
			args.add(application.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getEnName())) {
			sql.append(" and en_name like CONCAT('%',?,'%')");
			args.add(application.getEnName());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getVersion())) {
//			sql.append(" and version like CONCAT('%',?,'%')");
			sql.append(" and version=?");
			args.add(application.getVersion());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getIcon())) {
			sql.append(" and icon like CONCAT('%',?,'%')");
			args.add(application.getIcon());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getBigIcon())) {
			sql.append(", big_icon like CONCAT('%',?,'%')");
			args.add(application.getBigIcon());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getDeveloper()!=null) {
			sql.append(", developer =?");
			args.add(application.getDeveloper());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getCatalogId() != null) {
			sql.append(" and catalog_id=?");
			args.add(application.getCatalogId());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getSize())) {
//			sql.append(" and size like CONCAT('%',?,'%')");
			sql.append(" and size=?");
			args.add(application.getSize());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getAppFilePath())) {
			sql.append(" and app_file_path like CONCAT('%',?,'%')");
			args.add(application.getAppFilePath());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getPlatform())) {
			sql.append(" and platform like CONCAT('%',?,'%')");
			args.add(application.getPlatform());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getSupportLanguages() != null) {
			sql.append(" and support_languages=?");
			args.add(application.getSupportLanguages());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getPrice() != null) {
			sql.append(" and price=?");
			args.add(application.getPrice());
			argTypes.add(8);// java.sql.Types type
		}
		if (application.getDownCount() != null) {
			sql.append(" and down_count=?");
			args.add(application.getDownCount());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getAppLevel() != null) {
			sql.append(" and app_level=?");
			args.add(application.getAppLevel());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getDescription())) {
			sql.append(" and description like CONCAT('%',?,'%')");
			args.add(application.getDescription());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getPermissionDesc())) {
			sql.append(" and permission_desc like CONCAT('%',?,'%')");
			args.add(application.getPermissionDesc());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getStatus() != null) {
			sql.append(" and (status&?)!=0");
			args.add(application.getStatus());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getCreateTime() != null) {
			sql.append(" and create_time=?");
			args.add(application.getCreateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (application.getUpdateTime() != null) {
			sql.append(" and update_time=?");
			args.add(application.getUpdateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getFeatures())) {
			sql.append(" and features like CONCAT('%',?,'%')");
			args.add(application.getFeatures());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getFeatures())) {
			sql.append(" and en_features like CONCAT('%',?,'%')");
			args.add(application.getFeatures());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getMainPic())) {
			sql.append(" and main_pic like CONCAT('%',?,'%')");
			args.add(application.getMainPic());
			argTypes.add(12);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public JsonPage<PortalApplication> queryByExemple(PortalApplication application,
			DataGridModel dgm) {
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(application.getLocal())) {
			viewName="v_application_en";
		}
		JsonPage<PortalApplication> jsonPage = new JsonPage<PortalApplication>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder("select * from ").append(
				viewName).append(" where 1=1");
		StringBuilder countSql=new StringBuilder("select count(1) from ").append(
				viewName).append(" where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		if (StringUtils.hasText(application.getAppName())) {
			sql.append(" and app_name like CONCAT('%',?,'%')");
			countSql.append(" and app_name like CONCAT('%',?,'%')");
			args.add(application.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getVersion())) {
			sql.append(" and version like CONCAT('%',?,'%')");
			countSql.append(" and version like CONCAT('%',?,'%')");
			args.add(application.getVersion());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getIcon())) {
			sql.append(" and icon like CONCAT('%',?,'%')");
			countSql.append(" and icon like CONCAT('%',?,'%')");
			args.add(application.getIcon());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getBigIcon())) {
			sql.append(" and big_icon like CONCAT('%',?,'%')");
			countSql.append(" and big_icon like CONCAT('%',?,'%')");
			args.add(application.getBigIcon());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getDeveloper())) {
			sql.append(" and developer=?");
			countSql.append(" and developer=?");
			args.add(application.getDeveloper());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getDeveloperWebsite())) {
			sql.append(" and website like CONCAT('%',?,'%')");
			countSql.append(" and website like CONCAT('%',?,'%')");
			args.add(application.getDeveloperWebsite());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getDeveloperEmail())) {
			sql.append(" and email like CONCAT('%',?,'%')");
			countSql.append(" and email like CONCAT('%',?,'%')");
			args.add(application.getDeveloperEmail());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getCatalogId() != null) {
			sql.append(" and catalog_id=?");
			countSql.append(" and catalog_id=?");
			args.add(application.getCatalogId());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getSize())) {
			sql.append(" and size like CONCAT('%',?,'%')");
			countSql.append(" and size like CONCAT('%',?,'%')");
			args.add(application.getSize());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getAppFilePath())) {
			sql.append(" and app_file_path like CONCAT('%',?,'%')");
			countSql.append(" and app_file_path like CONCAT('%',?,'%')");
			args.add(application.getAppFilePath());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getPlatform())) {
			sql.append(" and platform like CONCAT('%',?,'%')");
			countSql.append(" and platform like CONCAT('%',?,'%')");
			args.add(application.getPlatform());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getSupportLanguages())) {
			sql.append(" and support_languages like CONCAT('%',?,'%')");
			countSql.append(" and support_languages like CONCAT('%',?,'%')");
			args.add(application.getSupportLanguages());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getPrice() != null) {
			sql.append(" and price=?");
			countSql.append(" and price=?");
			args.add(application.getPrice());
			argTypes.add(8);// java.sql.Types type
		}
		if (application.getDownCount() != null) {
			sql.append(" and down_count=?");
			countSql.append(" and down_count=?");
			args.add(application.getDownCount());
			argTypes.add(4);// java.sql.Types type
		}
		if (application.getAppLevel() != null) {
			sql.append(" and app_level=?");
			countSql.append(" and app_level=?");
			args.add(application.getAppLevel());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getDescription())) {
			sql.append(" and description like CONCAT('%',?,'%')");
			countSql.append(" and description like CONCAT('%',?,'%')");
			args.add(application.getDescription());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getPermissionDesc())) {
			sql.append(" and permission_desc like CONCAT('%',?,'%')");
			countSql.append(" and permission_desc like CONCAT('%',?,'%')");
			args.add(application.getPermissionDesc());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getFeatures())) {
			sql.append(" and features like CONCAT('%',?,'%')");
			countSql.append(" and features like CONCAT('%',?,'%')");
			args.add(application.getFeatures());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getStatus()!=null) {
			sql.append(" and (status&?)!=0");
			countSql.append(" and (status&?)!=0");
			args.add(application.getStatus());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(application.getMainPic())) {
			sql.append(" and main_pic like CONCAT('%',?,'%')");
			countSql.append(" and main_pic like CONCAT('%',?,'%')");
			args.add(application.getMainPic());
			argTypes.add(12);// java.sql.Types type
		}
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
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
		// 更新
		jsonPage.setTotal(totalRow);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, portalRowMapper));
		return jsonPage;
	}
	
	public PortalApplication query(int id, String local){
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		String sql = "select * from "+viewName+" where id=?";
		log.debug(String.format("\n%1$s\n", sql, id));
		return query(sql, id, portalRowMapper);
	}

	@Override
	public List<PortalApplication> queryHotFree(String local, int limit) {
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		String sql="select * from "+viewName+" where price=0 order by down_count desc limit ?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.query(sql, new Object[]{limit}, portalRowMapper);
	}

	@Override
	public List<PortalApplication> queryHotNewFree(String local, int limit) {
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		String sql="select * from "+viewName+" where price=0 order by down_count,update_time desc limit ?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.query(sql, new Object[]{limit}, portalRowMapper);
	}

	@Override
	public int checkApplication(String appName, String local) {
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		String sql="select 1 from "+viewName+" where app_name=?";
		log.debug(String.format("\n%1$s\n", sql));
		int result=0;
		try {
			result=jdbcTemplate.queryForInt(sql, appName);
		} catch (Exception e) {
			log.warn("no application name of ["+appName+"]...");
			log.warn(e.getMessage());
		}
		return result;
	}

	@Override
	public JsonPage<PortalApplication> fullTextSearch(String local,
			String ids, DataGridModel dgm) {
		JsonPage<PortalApplication> jsonPage = new JsonPage<PortalApplication>(dgm.getPage(), dgm.getRows());
		if (!StringUtils.hasText(ids)) {
			return jsonPage;
		}
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		StringBuilder sql = new StringBuilder("select * from ").append(
				viewName).append(" where id in (").append(ids).append(")");
		StringBuilder countSql=new StringBuilder("select count(1) from ").append(
				viewName).append(" where id in (").append(ids).append(")");
		log.debug(String.format("\n%1$s\n", sql));
		int totalRow = jdbcTemplate.queryForInt(countSql.toString());
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
		log.debug(String.format("\n%1$s\n", sql));
		List<PortalApplication> rows=jdbcTemplate.query(sql.toString(), new Object[]{jsonPage.getStartRow(), jsonPage.getPageSize()}, portalRowMapper);
		jsonPage.setRows(rows);
		return jsonPage;
	}

	@Override
	public JsonPage<PortalApplication> queryUserDownApps(String local,
			int userId, DataGridModel dgm) {
		JsonPage<PortalApplication> jsonPage = new JsonPage<PortalApplication>(dgm.getPage(), dgm.getRows());
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		StringBuilder sql = new StringBuilder("select a.* from ")
				.append(viewName)
				.append(" a where exists (select 1 from t_app_download_log where app_id=a.id and account_id=?)");
		StringBuilder countSql = new StringBuilder("select count(1) from ")
				.append(viewName)
				.append(" a where exists (select 1 from t_app_download_log where app_id=a.id and account_id=?)");
		log.debug(String.format("\n%1$s\n", sql));
		int totalRow = jdbcTemplate.queryForInt(countSql.toString(), userId);
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
		log.debug(String.format("\n%1$s\n", sql));
		List<PortalApplication> rows = jdbcTemplate.query(
				sql.toString(),
				new Object[] { userId, jsonPage.getStartRow(),
						jsonPage.getPageSize() }, portalRowMapper);
		jsonPage.setRows(rows);
		return jsonPage;
	}

	@Override
	public JsonPage<PortalApplication> queryUserDownReferencedApps(
			String local, int appId, DataGridModel dgm) {
		JsonPage<PortalApplication> jsonPage = new JsonPage<PortalApplication>(dgm.getPage(), dgm.getRows());
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(local)) {
			viewName="v_application_en";
		}
		StringBuilder sql = new StringBuilder("select a.* from ")
				.append(viewName)
				.append(" a left join t_app_download_log b on a.id=b.app_id left join t_app_download_log c on b.account_id=c.account_id where c.app_id=? and b.app_id!=? group by b.app_id");
		StringBuilder countSql = new StringBuilder("select count(distinct a.id) from ")
				.append(viewName)
				.append(" a left join t_app_download_log b on a.id=b.app_id left join t_app_download_log c on b.account_id=c.account_id where c.app_id=? and b.app_id!=?");
		log.debug(String.format("\n%1$s\n", sql));
		int totalRow = jdbcTemplate.queryForInt(countSql.toString(), appId, appId);
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
		log.debug(String.format("\n%1$s\n", sql));
		List<PortalApplication> rows = jdbcTemplate.query(
				sql.toString(),
				new Object[] { appId, appId, jsonPage.getStartRow(),
						jsonPage.getPageSize() }, portalRowMapper);
		jsonPage.setRows(rows);
		return jsonPage;
	}

	@Override
	public List<UpdateNotice> queryUpdatedApplication() {
		String sql="select a.id, a.app_name, a.en_name,a.version, b.account_id"
				+" from t_application a, v_app_download_log_cn b"
				+" where a.id=b.app_id and a.version!=b.version and a.status>0";
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.query(sql, new RowMapper<UpdateNotice>(){
				@Override
				public UpdateNotice mapRow(ResultSet rs, int rowNum) throws SQLException {
					UpdateNotice notice=new UpdateNotice();
					notice.setAppId(rs.getInt("id"));
					notice.setAppName(rs.getString("app_name"));
					notice.setAppEnName(rs.getString("en_name"));
					notice.setAppVesion(rs.getString("version"));
					notice.setAccountId(rs.getInt("account_id"));
					return notice;
				}
			});
		} catch (Exception e) {
			log.warn("no user downloaded applications are update...");
		}
		return Collections.emptyList();
	}
	
}
