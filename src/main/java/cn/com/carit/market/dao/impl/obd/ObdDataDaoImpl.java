package cn.com.carit.market.dao.impl.obd;

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

import cn.com.carit.market.bean.obd.ObdData;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.obd.ObdDataDao;
@Repository
public class ObdDataDaoImpl extends BaseDaoImpl implements ObdDataDao {
	
	private final RowMapper<ObdData> rowMapper=new RowMapper<ObdData>() {
		
		@Override
		public ObdData mapRow(ResultSet rs, int rowNum) throws SQLException {
			ObdData t=new ObdData();
			t.setId(rs.getInt("id"));
			t.setDate(rs.getTimestamp("date"));
			t.setDeviceId(rs.getString("device_id"));
			t.setLocation(rs.getString("location"));
			t.setCreateTime(rs.getTimestamp("create_time"));
			for (int i = 0; i < t.getValues().length; i++) {
				t.getValues()[i]=rs.getInt("value_"+(i+1));
			}
			t.setError(rs.getString("error"));
			return t;
		}
	};

	@Override
	public int add(final ObdData t) {
		final String sql="insert into t_obd_data(date,device_id,location,create_time"
				+",value_1"
				+",value_2"
				+",value_3"
				+",value_4"
				+",value_5"
				+",value_6"
				+",value_7"
				+",value_8"
				+",value_9"
				+",value_10"
				+",value_11"
				+",value_12"
				+",value_13"
				+",value_14"
				+",value_15"
				+",value_16"
				+",value_17"
				+",value_18"
				+",value_19"
				+") values(?,?,?,now()"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+",?"
				+")";
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
				ps.setDate(i++, new Date(t.getDate().getTime()));
				ps.setString(i++, t.getDeviceId());
				ps.setString(i++, t.getLocation());
				for (int j = 0; j < t.getValues().length; j++) {
					ps.setInt(i++, t.getValues()[j]);
				}
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql="delete from t_obd_data where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int batchDelete(String ids) {
		String sql="delete from t_obd_data where id in ("+ids+")";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.update(sql);
	}

	/*@Override
	public int update(ObdData t) {
		return 0;
	}*/

	@Override
	public ObdData queryById(int id) {
		String sql="select * from t_obd_data where id=?";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, id, rowMapper);
	}
	
	@Override
	public ObdData queryLastByDeviceId(String deviceId) {
		String sql="select * from t_obd_data where device_id=? order by create_time desc limit 1";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql, deviceId, rowMapper);
	}

	@Override
	public List<ObdData> query() {
		String sql="select * from t_obd_data";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.query(sql, rowMapper);
	}

	@Override
	public List<ObdData> queryByExemple(ObdData t) {
		StringBuilder sql = new StringBuilder(
				"select * from t_obd_data where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, t));
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<ObdData> queryByExemple(ObdData t, DataGridModel dgm) {
		JsonPage<ObdData> jsonPage = new JsonPage<ObdData>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_obd_data where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql=buildWhere(args, argTypes, t);
		sql.append(whereSql);
		String countSql="select count(1) from t_obd_data where 1=1"+whereSql;
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
			sql.append(" order by date desc");
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

	private String buildWhere(List<Object> args, List<Integer> argTypes, ObdData t) {
		StringBuilder sql=new StringBuilder();
		if (StringUtils.hasText(t.getDeviceId())) {
			sql.append(" and device_id=?");
			args.add(t.getDeviceId());
			argTypes.add(Types.VARCHAR);
		}
		if (t.getStartDate()!=null) {
			sql.append(" and date>=?");
			args.add(t.getStartDate());
			argTypes.add(Types.DATE);
		}
		if (t.getEndDate()!=null) {
			sql.append(" and date<=?");
			args.add(t.getEndDate());
			argTypes.add(Types.DATE);
		}
		if (StringUtils.hasText(t.getLocation())) {
			sql.append(" and location like CONCAT('%',?,'%')");
			args.add(t.getLocation());
			argTypes.add(Types.VARCHAR);
		}
		return sql.toString();
	}
}
