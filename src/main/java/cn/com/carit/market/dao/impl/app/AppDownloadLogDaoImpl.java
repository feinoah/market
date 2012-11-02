package cn.com.carit.market.dao.impl.app;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.portal.AppDownStat;
import cn.com.carit.market.bean.portal.PortalAppDownloadLog;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppDownloadLogDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AppDownloadLogDaoImpl Auto generated Code
 */
@Repository
public class AppDownloadLogDaoImpl extends BaseDaoImpl implements
		AppDownloadLogDao {
	private final RowMapper<AppDownloadLog> rowMapper = new RowMapper<AppDownloadLog>() {

		@Override
		public AppDownloadLog mapRow(ResultSet rs, int rowNum)
				throws SQLException {
			AppDownloadLog appDownloadLog = new AppDownloadLog();
			appDownloadLog.setId(rs.getInt("id"));
			appDownloadLog.setAccountId(rs.getInt("account_id"));
			appDownloadLog.setAppId(rs.getInt("app_id"));
			appDownloadLog.setVersion(rs.getString("version"));
			appDownloadLog.setDownloadTime(rs.getTimestamp("download_time"));
			appDownloadLog.setAppName(rs.getString("app_name"));
			appDownloadLog.setEnName(rs.getString("en_name"));
			appDownloadLog.setUserName(rs.getString("nick_name"));
			return appDownloadLog;
		}
	};

	@Override
	public int add(final AppDownloadLog appDownloadLog) {
		final String sql = "insert into t_app_download_log (account_id"
				+ ", app_id, version, download_time" + ") values (?, ?, ?, now())";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		KeyHolder gkHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				int i=1;
				ps.setInt(i++, appDownloadLog.getAccountId());
				ps.setInt(i++, appDownloadLog.getAppId());
				ps.setString(i++, appDownloadLog.getVersion());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_app_download_log where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int deleteByAppId(int appId) {
		String sql = "delete from t_app_download_log where app_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, appId);
	}

	@Override
	public int update(AppDownloadLog appDownloadLog) {
		StringBuilder sql = new StringBuilder(
				"update t_app_download_log set 1=1");
		List<Object> args = new ArrayList<Object>();
		sql.append(" where id=?");
		if (appDownloadLog.getAccountId() != null) {
			sql.append(", account_id=?");
			args.add(appDownloadLog.getAccountId());
		}
		if (appDownloadLog.getAppId() != null) {
			sql.append(", app_id=?");
			args.add(appDownloadLog.getAppId());
		}
		if (appDownloadLog.getDownloadTime() != null) {
			sql.append(", download_time=?");
			args.add(appDownloadLog.getDownloadTime());
		}
		sql.append(" where id=?");
		args.add(appDownloadLog.getId());
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public AppDownloadLog queryById(int id) {
		String sql = "select * from t_app_download_log where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}

	@Override
	public List<AppDownloadLog> query() {
		String sql="select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return this.jdbcTemplate.query(sql,rowMapper);
	}

	@Override
	public List<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog) {
		StringBuilder sql = new StringBuilder(
				"select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, appDownloadLog));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog,
			DataGridModel dgm) {
		JsonPage<AppDownloadLog> jsonPage = new JsonPage<AppDownloadLog>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select a.*,b.app_name, b.en_name, c.nick_name from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, appDownloadLog);
		sql.append(whereSql);
		String countSql="select count(1) from t_app_download_log a left join t_application b on a.app_id=b.id left join t_account_info c on a.account_id=c.id where 1=1"+whereSql;
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
			sql.append(" order by a.download_time desc");
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	private String buildWhere(List<Object> args,
			List<Integer> argTypes, AppDownloadLog appDownloadLog) {
		StringBuilder sql=new StringBuilder();
		if (appDownloadLog.getAccountId() != null) {
			sql.append(" and a.account_id=?");
			args.add(appDownloadLog.getAccountId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(appDownloadLog.getUserName())){
			sql.append(" and  c.nick_name like CONCAT('%',?,'%')");
			args.add(appDownloadLog.getUserName());
			argTypes.add(12);// java.sql.Types type
		}
		if (appDownloadLog.getAppId() != null) {
			sql.append(" and a.app_id=?");
			args.add(appDownloadLog.getAppId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(appDownloadLog.getAppName())){
			sql.append(" and b.app_name like CONCAT('%',?,'%')");
			args.add(appDownloadLog.getAppName());
			argTypes.add(12);// java.sql.Types type
		}
		if(StringUtils.hasText(appDownloadLog.getEnName())){
			sql.append(" and  b.en_name like CONCAT('%',?,'%')");
			args.add(appDownloadLog.getEnName());
			argTypes.add(12);// java.sql.Types type
		}
		if (appDownloadLog.getDownloadTime() != null) {
			sql.append(" and a.download_time=?");
			args.add(appDownloadLog.getDownloadTime());
			argTypes.add(93);// java.sql.Types type
		}
		return sql.toString();
	}

	@Override
	public JsonPage<PortalAppDownloadLog> queryByExemple(PortalAppDownloadLog appDownloadLog,
			DataGridModel dgm) {
		JsonPage<PortalAppDownloadLog> jsonPage = new JsonPage<PortalAppDownloadLog>(dgm.getPage(), dgm.getRows());
		String viewName="v_app_download_log_cn";
		if (Constants.LOCAL_EN.equalsIgnoreCase(appDownloadLog.getLocal())) {
			viewName="v_app_download_log_en";
		}
		StringBuilder sql = new StringBuilder(
				"select * from ").append(viewName).append(" where 1=1 ");
		StringBuilder countSql = new StringBuilder(
				"select count(1) from ").append(viewName).append(" where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		if (appDownloadLog.getAccountId() >0) {
			sql.append(" and account_id=?");
			countSql.append(" and account_id=?");
			args.add(appDownloadLog.getAccountId());
			argTypes.add(4);// java.sql.Types type
		}
		if (appDownloadLog.getAppId() != null) {
			sql.append(" and app_id=?");
			countSql.append(" and app_id=?");
			args.add(appDownloadLog.getAppId());
			argTypes.add(4);// java.sql.Types type
		}
		if(StringUtils.hasText(appDownloadLog.getAppName())) {
			sql.append(" and app_name like CONCAT('%',?,'%')");
			countSql.append(" and app_name like CONCAT('%',?,'%')");
			args.add(appDownloadLog.getAppName());
			argTypes.add(Types.VARCHAR);// java.sql.Types type
		}
		log.debug(String.format("\n%1$s\n", countSql));
		int totalRow=queryForInt(countSql.toString(), args, argTypes);
		// 排序
		if (StringUtils.hasText(dgm.getOrder())
				&& StringUtils.hasText(dgm.getSort())) {
			sql.append(" order by ")
					.append(StringUtil.splitFieldWords(dgm.getSort()))
					.append(" ").append(dgm.getOrder());
		}
		sql.append(" limit ?, ?");
		args.add(jsonPage.getStartRow());
		args.add(jsonPage.getPageSize());
		argTypes.add(Types.INTEGER);
		argTypes.add(Types.INTEGER);
		jsonPage.setTotal(totalRow);
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		jsonPage.setRows(query(sql.toString(), args, argTypes, new RowMapper<PortalAppDownloadLog>() {

			@Override
			public PortalAppDownloadLog mapRow(ResultSet rs, int rowNum) throws SQLException {
				PortalAppDownloadLog log=new PortalAppDownloadLog();
				log.setAccountId(rs.getInt("account_id"));
				log.setAppId(rs.getInt("app_id"));
				log.setVersion(rs.getString("version"));
				log.setAppName(rs.getString("app_name"));
				log.setDownloadTime(rs.getTimestamp("download_time"));
				return log;
			}
		}));
		return jsonPage;
	}

	@Override
	public int checkUserDownLog(int accountId, int appId) {
		String sql="select count(1) from t_app_download_log where account_id=? and app_id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		try {
			return jdbcTemplate.queryForInt(sql, accountId, appId);
		} catch (Exception e) {
			log.warn("not exist record of accountId["+accountId+"] or appId["+appId+"]");
			log.warn(e.getMessage());
		}
		return 0;
	}

	@Override
	public List<AppDownStat> statAppDownlog(int appId, Date startDate) {
		String sql="select download_time date, count(1) count" 
				+ " from t_app_download_log where app_id=? and download_time>?"
				+ " group by date_format(download_time,'%Y-%c-%d')";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, new Object[]{appId, startDate}
		, new int[]{Types.INTEGER, Types.TIMESTAMP}, new RowMapper<AppDownStat>() {
			@Override
			public AppDownStat mapRow(ResultSet rs, int rowNum) throws SQLException {
				AppDownStat stat=new AppDownStat();
				stat.setDate(rs.getDate("date"));
				stat.setCount(rs.getInt("count"));
				return stat;
			}
		});
	}
	
}
