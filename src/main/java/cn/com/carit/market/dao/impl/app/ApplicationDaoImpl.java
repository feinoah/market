package cn.com.carit.market.dao.impl.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.Application;
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
			application.setImages(rs.getString("images"));
			application.setStatus(rs.getInt("status"));
			application.setCreateTime(rs.getTimestamp("create_time"));
			application.setUpdateTime(rs.getTimestamp("update_time"));
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
			application.setSupportLanguages(rs.getInt("support_languages"));
			application.setPrice(rs.getDouble("price"));
			application.setDownCount(rs.getInt("down_count"));
			application.setAppLevel(rs.getInt("app_level"));
			application.setDescription(rs.getString("description"));
			application.setPermissionDesc(rs.getString("permission_desc"));
			application.setImages(rs.getString("images"));
			return application;
		}
	};

	@Override
	public int add(final Application application) {
		final String sql = "insert into t_application (app_name"
				+ ", en_name, version, icon, catalog_id"
				+ ", size, app_file_path, platform"
				+ ", support_languages, price" + ", down_count"
				+ ", app_level, description , permission_desc, en_description , en_permission_desc"
				+ ", images" + ", status" + ", create_time" + ", update_time"
				+ ") values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,now(),now())";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, 
				application.getAppName(),
				application.getEnName(), 
				application.getVersion(),
				application.getIcon(), 
				application.getCatalogId(),
				application.getSize(), 
				application.getAppFilePath(),
				application.getPlatform(), 
				application.getSupportLanguages(),
				application.getPrice(), 
				application.getDownCount(),
				application.getAppLevel(), 
				application.getDescription(),
				application.getPermissionDesc(),
				application.getEnDescription(),
				application.getEnPermissionDesc(), 
				application.getImages(),
				application.getStatus());
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
			sql.append(", status=?");
			args.add(application.getStatus());
		}
		if (StringUtils.hasText(application.getImages())) {
			sql.append(", images=?");
			args.add(application.getImages());
		}
		sql.append(" where id=?");
		args.add(application.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public Application queryById(int id) {
		String sql = "select * from t_application where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public List<Application> query() {
		return this.jdbcTemplate
				.query("select * from t_application", rowMapper);
	}

	@Override
	public List<Application> queryByExemple(Application application) {
		StringBuilder sql = new StringBuilder(
				"select * from t_application where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, application);
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage queryByExemple(Application application, DataGridModel dgm) {
		JsonPage jsonPage = new JsonPage(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_application where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, application);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getEndRow());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		int totalRow = getCount(application);
		// 更新
		jsonPage.setTotal(totalRow);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	@Override
	public int getCount(Application application) {
		StringBuilder sql = new StringBuilder(
				"select count(1) from t_application where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, application);
		log.debug(String.format("\n%1$s\n", sql));
		return queryForInt(sql.toString(), args, argTypes);
	}

	private void buildWhere(StringBuilder sql, List<Object> args,
			List<Integer> argTypes, Application application) {
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
			sql.append(" and version like CONCAT('%',?,'%')");
			args.add(application.getVersion());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getIcon())) {
			sql.append(" and icon like CONCAT('%',?,'%')");
			args.add(application.getIcon());
			argTypes.add(12);// java.sql.Types type
		}
		if (application.getCatalogId() != null) {
			sql.append(" and catalog_id=?");
			args.add(application.getCatalogId());
			argTypes.add(4);// java.sql.Types type
		}
		if (StringUtils.hasText(application.getSize())) {
			sql.append(" and size like CONCAT('%',?,'%')");
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
			sql.append(" and status=?");
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
	}

	@Override
	public JsonPage queryByExemple(PortalApplication application,
			DataGridModel dgm) {
		String viewName="v_application_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(application.getLocal())) {
			viewName="v_application_en";
		}
		JsonPage jsonPage = new JsonPage(dgm.getPage(), dgm.getRows());
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
		if (application.getSupportLanguages() != null) {
			sql.append(" and support_languages=?");
			countSql.append(" and support_languages=?");
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
		log.debug(String.format("\n%1$s\n", sql));
		int totalRow = queryForInt(countSql.toString(), args, argTypes);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getEndRow());
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
		log.debug(String.format("\n%1$s\n", sql));
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
	
}
