package cn.com.carit.market.dao.impl;

import java.sql.Types;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import cn.com.carit.market.common.utils.DataUtils;

@Repository
public class BaseDaoImpl {
	@Resource(name = "jdbcTemplate")
	protected JdbcTemplate jdbcTemplate;
	protected final Logger log = Logger.getLogger(getClass());
	
	public <T> T query(String sql, List<Object> args, List<Integer> argTypes, ResultSetExtractor<T> rse) {
		return this.jdbcTemplate.query(sql, args.toArray(), DataUtils.listToIntArray(argTypes), rse);
	}
	
	public void query(String sql, List<Object> args, List<Integer> argTypes, RowCallbackHandler rch) {
		this.jdbcTemplate.query(sql, args.toArray(), DataUtils.listToIntArray(argTypes), rch);
	}
	
	public <T> List<T> query(String sql, List<Object> args, List<Integer> argTypes, RowMapper<T> rowMapper) {
		return this.jdbcTemplate.query(sql, args.toArray(), DataUtils.listToIntArray(argTypes), rowMapper);
	}
	
	/**
	 * 按唯一字段查询，唯一字段只能是Integer/Long/String
	 * @param sql
	 * @param id
	 * @param rowMapper
	 * @return
	 */
	public <T> T query(String sql, Object id, RowMapper<T> rowMapper){
		int idType=Types.INTEGER;
		if (id instanceof Long) {
			idType=Types.BIGINT;
		}
		if (id instanceof String) {
			idType=Types.VARCHAR;
		}
		return this.jdbcTemplate.queryForObject(sql, new Object[]{id}, new int[]{idType}, rowMapper);
	}
	
	public <T> T queryForObject(String sql, List<Object> args, List<Integer> argTypes, RowMapper<T> rowMapper) {
		return this.jdbcTemplate.queryForObject(sql, args.toArray(), DataUtils.listToIntArray(argTypes), rowMapper);
	}
	
	public <T> T queryForObject(String sql, List<Object> args, List<Integer> argTypes, Class<T> requiredType) {
		return this.jdbcTemplate.queryForObject(sql, args.toArray(), DataUtils.listToIntArray(argTypes), requiredType);
	}
	
	public Map<String, Object> queryForMap(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.queryForMap(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
	public long queryForLong(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.queryForLong(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
	public int queryForInt(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.queryForInt(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
	public <T> List<T> queryForList(String sql, List<Object> args, List<Integer> argTypes, Class<T> elementType) {
		return this.jdbcTemplate.queryForList(sql, args.toArray(), DataUtils.listToIntArray(argTypes), elementType);
	}
	
	public List<Map<String, Object>> queryForList(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.queryForList(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
	public SqlRowSet queryForRowSet(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.queryForRowSet(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
	public int update(String sql, List<Object> args, List<Integer> argTypes) {
		return this.jdbcTemplate.update(sql, args.toArray(), DataUtils.listToIntArray(argTypes));
	}
	
}
