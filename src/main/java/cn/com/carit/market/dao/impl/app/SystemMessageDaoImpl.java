package cn.com.carit.market.dao.impl.app;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.SystemMessageDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
@Repository
public class SystemMessageDaoImpl extends BaseDaoImpl implements SystemMessageDao {

	private final RowMapper<SystemMessage> rowMapper=new RowMapper<SystemMessage>() {
		
		@Override
		public SystemMessage mapRow(ResultSet rs, int rowNum) throws SQLException {
			SystemMessage systemMessage=new SystemMessage();
			systemMessage.setId(rs.getInt("id"));
			systemMessage.setAccountId(rs.getInt("account_id"));
			systemMessage.setCatalog(rs.getInt("catalog"));
			systemMessage.setTitle(rs.getString("title"));
			systemMessage.setContent(rs.getString("content"));
			systemMessage.setStatus(rs.getInt("status"));
			systemMessage.setCreateTime(rs.getTimestamp("create_time"));
			systemMessage.setUpdateTime(rs.getTimestamp("update_time"));
			return systemMessage;
		}
	};
	@Override
	public int add(SystemMessage systemMessage) {
		String sql="insert into t_sys_message(account_id, catalog,title, content, create_time, update_time)"
				+" values (?, ?,?, ?, now(), now())";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql
				, systemMessage.getAccountId()
				, systemMessage.getCatalog()
				, systemMessage.getTitle()
				, systemMessage.getContent());
	}

	@Override
	public int delete(int id) {
		String sql="delete from t_sys_message where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int batchDelete(String ids) {
		String sql = "delete from t_sys_message where id in("+ids+")";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql);
	}

	@Override
	public int update(SystemMessage systemMessage) {
		StringBuilder sql = new StringBuilder(
				"update t_sys_message set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (systemMessage.getAccountId()!=null) {
			sql.append(", account_id=?");
			args.add(systemMessage.getAccountId());
		}
		if (systemMessage.getCatalog()!=null) {
			sql.append(", catalog=?");
			args.add(systemMessage.getCatalog());
		}
		if (StringUtils.hasText(systemMessage.getTitle())) {
			sql.append(", title=?");
			args.add(systemMessage.getTitle());
		}
		if (systemMessage.getStatus()!=null) {
			sql.append(", status=?");
			args.add(systemMessage.getStatus());
		}
		if (StringUtils.hasText(systemMessage.getContent())) {
			sql.append(", content=?");
			args.add(systemMessage.getContent());
		}
		sql.append(" where id=?");
		args.add(systemMessage.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public SystemMessage queryById(int id) {
		String sql="select * from t_sys_message where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public List<SystemMessage> query() {
		String sql="select * from t_sys_message";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public List<SystemMessage> queryByExemple(SystemMessage systemMessage) {
		StringBuilder sql = new StringBuilder(
				"select * from t_sys_message where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, systemMessage));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<SystemMessage> queryByExemple(SystemMessage systemMessage,
			DataGridModel dgm) {
		JsonPage<SystemMessage> jsonPage = new JsonPage<SystemMessage>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_sys_message where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, systemMessage);
		sql.append(whereSql);
		String countSql="select count(1) from t_sys_message where 1=1"+whereSql;
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
			sql.append(" order by status asc ,update_time desc");
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

	private String buildWhere(List<Object> args, List<Integer> argTypes, SystemMessage systemMessage){
		StringBuilder sql=new StringBuilder();
		if (systemMessage.getAccountId()!=null) {
			sql.append(" and account_id=?");
			args.add(systemMessage.getAccountId());
			argTypes.add(Types.INTEGER);
		}
		if (systemMessage.getCatalog()!=null) {
			sql.append(" and catalog=?");
			args.add(systemMessage.getCatalog());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(systemMessage.getTitle())) {
			sql.append(" and title like CONCAT('%',?,'%')");
			args.add(systemMessage.getTitle());
			argTypes.add(Types.VARCHAR);
		}
		if (systemMessage.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(systemMessage.getStatus());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(systemMessage.getContent())) {
			sql.append(" and content like CONCAT('%',?,'%')");
			args.add(systemMessage.getContent());
			argTypes.add(Types.VARCHAR);
		}
		return sql.toString();
	}

	@Override
	public int queryUnreadCountByAccountId(int accountId) {
		String sql="select count(1) from t_sys_message where account_id=? and status=?";
		try {
			return jdbcTemplate.queryForInt(sql, accountId, SystemMessage.STATUS_UNREAD);
		} catch (Exception e) {
			log.warn("there is no message of this account["+accountId+"]", e);
		}
		return 0;
	}

	@Override
	public int checkSystemMessage(SystemMessage systemMessage) {
		StringBuilder sql=new StringBuilder("select count(1) from t_sys_message where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		if (systemMessage.getAccountId()!=null) {
			sql.append(" and account_id=?");
			args.add(systemMessage.getAccountId());
			argTypes.add(Types.INTEGER);
		}
		if (systemMessage.getCatalog()!=null) {
			sql.append(" and catalog=?");
			args.add(systemMessage.getCatalog());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(systemMessage.getTitle())) {
			sql.append(" and title like CONCAT('%',?,'%')");
			args.add(systemMessage.getTitle());
			argTypes.add(Types.VARCHAR);
		}
		if (systemMessage.getStatus()!=null) {
			sql.append(" and status=?");
			args.add(systemMessage.getStatus());
			argTypes.add(Types.INTEGER);
		}
		if (StringUtils.hasText(systemMessage.getContent())) {
			sql.append(" and content like CONCAT('%',?,'%')");
			args.add(systemMessage.getContent());
			argTypes.add(Types.VARCHAR);
		}
		try {
			return queryForInt(sql.toString(), args, argTypes);
		} catch (Exception e) {
			log.warn("there is no message of this exemple "+systemMessage, e);
		}
		return 0;
	}
	
}
