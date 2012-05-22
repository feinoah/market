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

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AccountInfoDao;
import cn.com.carit.market.dao.impl.BaseDaoImpl;

/**
 * AccountInfoDaoImpl Auto generated Code
 */
@Repository
public class AccountInfoDaoImpl extends BaseDaoImpl implements AccountInfoDao {
	private final RowMapper<AccountInfo> rowMapper = new RowMapper<AccountInfo>() {

		@Override
		public AccountInfo mapRow(ResultSet rs, int rowNum) throws SQLException {
			AccountInfo accountInfo = new AccountInfo();
			accountInfo.setId(rs.getInt("id"));
			accountInfo.setEmail(rs.getString("email"));
			accountInfo.setPassword(rs.getString("password"));
			accountInfo.setNickName(rs.getString("nick_name"));
			accountInfo.setGender(rs.getByte("gender"));
			accountInfo.setBirthday(rs.getTimestamp("birthday"));
			accountInfo.setPhoto(rs.getString("photo"));
			accountInfo.setBalance(rs.getDouble("balance"));
			accountInfo.setRealName(rs.getString("real_name"));
			accountInfo.setIdCard(rs.getString("id_card"));
			accountInfo.setOfficePhone(rs.getString("office_phone"));
			accountInfo.setMobile(rs.getString("mobile"));
			accountInfo.setAddress(rs.getString("address"));
			accountInfo.setLastLoginIp(rs.getString("last_login_ip"));
			accountInfo.setLastLoginTime(rs.getTimestamp("last_login_time"));
			accountInfo.setStatus(rs.getByte("status"));
			accountInfo.setUpdateTime(rs.getTimestamp("update_time"));
			accountInfo.setCreateTime(rs.getTimestamp("create_time"));
			return accountInfo;
		}
	};

	@Override
	public int add(final AccountInfo accountInfo) {
		final String sql = "insert into t_account_info (" + "	email"
				+ ", password" + ", nick_name" + ", gender" + ", birthday"
				+ ", photo" + ", balance" + ", real_name" + ", id_card"
				+ ", office_phone" + ", mobile" + ", address" + ", update_time"
				+ ", create_time" + ") values (" + "	?" + ", ?" + ", ?" + ", ?"
				+ ", ?" + ", ?" + ", ?" + ", ?" + ", ?" + ", ?" + ", ?" + ", ?"
				+ ", now()" + ", now()" + ")";
		log.debug(String.format("\n%1$s\n", sql));
		KeyHolder gkHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, accountInfo.getEmail());
				ps.setString(2, accountInfo.getPassword());
				ps.setString(3, accountInfo.getNickName());
				ps.setByte(4, accountInfo.getGender());
				if (accountInfo.getBirthday() != null) {
					ps.setDate(5, new Date(accountInfo.getBirthday().getTime()));
				} else {
					ps.setDate(5, null);
				}
				ps.setString(6, accountInfo.getPhoto());
				ps.setDouble(7, accountInfo.getBalance());
				ps.setString(8, accountInfo.getRealName());
				ps.setString(9, accountInfo.getIdCard());
				ps.setString(10, accountInfo.getOfficePhone());
				ps.setString(11, accountInfo.getMobile());
				ps.setString(12, accountInfo.getAddress());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_account_info where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int update(AccountInfo accountInfo) {
		StringBuilder sql = new StringBuilder(
				"update t_account_info set update_time=now()");
		List<Object> args = new ArrayList<Object>();
		if (StringUtils.hasText(accountInfo.getEmail())) {
			sql.append(", email=?");
			args.add(accountInfo.getEmail());
		}
//		if (StringUtils.hasText(accountInfo.getPassword())) {
//			sql.append(", password=?");
//			args.add(accountInfo.getPassword());
//		}
		if (StringUtils.hasText(accountInfo.getNickName())) {
			sql.append(", nick_name=?");
			args.add(accountInfo.getNickName());
		}
		if (accountInfo.getGender() != null) {
			sql.append(", gender=?");
			args.add(accountInfo.getGender());
		}
		if (accountInfo.getBirthday() != null) {
			sql.append(", birthday=?");
			args.add(accountInfo.getBirthday());
		}
		if (StringUtils.hasText(accountInfo.getPhoto())) {
			sql.append(", photo=?");
			args.add(accountInfo.getPhoto());
		}
		if (accountInfo.getBalance() != null) {
			sql.append(", balance=?");
			args.add(accountInfo.getBalance());
		}
		if (StringUtils.hasText(accountInfo.getRealName())) {
			sql.append(", real_name=?");
			args.add(accountInfo.getRealName());
		}
		if (StringUtils.hasText(accountInfo.getIdCard())) {
			sql.append(", id_card=?");
			args.add(accountInfo.getIdCard());
		}
		if (StringUtils.hasText(accountInfo.getOfficePhone())) {
			sql.append(", office_phone=?");
			args.add(accountInfo.getOfficePhone());
		}
		if (StringUtils.hasText(accountInfo.getMobile())) {
			sql.append(", mobile=?");
			args.add(accountInfo.getMobile());
		}
		if (StringUtils.hasText(accountInfo.getAddress())) {
			sql.append(", address=?");
			args.add(accountInfo.getAddress());
		}
		if (StringUtils.hasText(accountInfo.getLastLoginIp())) {
			sql.append(", last_login_ip=?");
			args.add(accountInfo.getLastLoginIp());
		}
		if (accountInfo.getLastLoginTime() != null) {
			sql.append(", last_login_time=?");
			args.add(accountInfo.getLastLoginTime());
		}
		if (accountInfo.getStatus() != null) {
			sql.append(", status=?");
			args.add(accountInfo.getStatus());
		}
		if (accountInfo.getUpdateTime() != null) {
			sql.append(", update_time=?");
			args.add(accountInfo.getUpdateTime());
		}
		if (accountInfo.getCreateTime() != null) {
			sql.append(", create_time=?");
			args.add(accountInfo.getCreateTime());
		}
		sql.append(" where id=?");
		args.add(accountInfo.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), args.toArray());
	}

	@Override
	public AccountInfo queryById(int id) {
		String sql = "select * from t_account_info where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public AccountInfo queryByEmail(String email) {
		String sql = "select * from t_account_info where email=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, email, rowMapper);
	}

	@Override
	public List<AccountInfo> query() {
		return this.jdbcTemplate.query("select * from t_account_info",
				rowMapper);
	}

	@Override
	public List<AccountInfo> queryByExemple(AccountInfo accountInfo) {
		StringBuilder sql = new StringBuilder(
				"select * from t_account_info where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, accountInfo);
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage queryByExemple(AccountInfo accountInfo, DataGridModel dgm) {
		JsonPage jsonPage = new JsonPage(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_account_info where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, accountInfo);
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
		int totalRow = getCount(accountInfo);
		// 更新
		jsonPage.setTotal(totalRow);
		log.debug(String.format("\n%1$s\n", sql));
		jsonPage.setRows(query(sql.toString(), args, argTypes, rowMapper));
		return jsonPage;
	}

	@Override
	public int getCount(AccountInfo accountInfo) {
		StringBuilder sql = new StringBuilder(
				"select count(1) from t_account_info where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		buildWhere(sql, args, argTypes, accountInfo);
		log.debug(String.format("\n%1$s\n", sql));
		return queryForInt(sql.toString(), args, argTypes);
	}

	private void buildWhere(StringBuilder sql, List<Object> args,
			List<Integer> argTypes, AccountInfo accountInfo) {
		if (StringUtils.hasText(accountInfo.getEmail())) {
			sql.append(" and email like CONCAT('%',?,'%')");
			args.add(accountInfo.getEmail());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getPassword())) {
			sql.append(" and password like CONCAT('%',?,'%')");
			args.add(accountInfo.getPassword());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getNickName())) {
			sql.append(" and nick_name like CONCAT('%',?,'%')");
			args.add(accountInfo.getNickName());
			argTypes.add(12);// java.sql.Types type
		}
		if (accountInfo.getGender() != null) {
			sql.append(" and gender=?");
			args.add(accountInfo.getGender());
			argTypes.add(-6);// java.sql.Types type
		}
		if (accountInfo.getBirthday() != null) {
			sql.append(" and birthday=?");
			args.add(accountInfo.getBirthday());
			argTypes.add(91);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getPhoto())) {
			sql.append(" and photo like CONCAT('%',?,'%')");
			args.add(accountInfo.getPhoto());
			argTypes.add(12);// java.sql.Types type
		}
		if (accountInfo.getBalance() != null) {
			sql.append(" and balance=?");
			args.add(accountInfo.getBalance());
			argTypes.add(8);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getRealName())) {
			sql.append(" and real_name like CONCAT('%',?,'%')");
			args.add(accountInfo.getRealName());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getIdCard())) {
			sql.append(" and id_card like CONCAT('%',?,'%')");
			args.add(accountInfo.getIdCard());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getOfficePhone())) {
			sql.append(" and office_phone like CONCAT('%',?,'%')");
			args.add(accountInfo.getOfficePhone());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getMobile())) {
			sql.append(" and mobile like CONCAT('%',?,'%')");
			args.add(accountInfo.getMobile());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getAddress())) {
			sql.append(" and address like CONCAT('%',?,'%')");
			args.add(accountInfo.getAddress());
			argTypes.add(12);// java.sql.Types type
		}
		if (StringUtils.hasText(accountInfo.getLastLoginIp())) {
			sql.append(" and last_login_ip like CONCAT('%',?,'%')");
			args.add(accountInfo.getLastLoginIp());
			argTypes.add(12);// java.sql.Types type
		}
		if (accountInfo.getLastLoginTime() != null) {
			sql.append(" and last_login_time=?");
			args.add(accountInfo.getLastLoginTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (accountInfo.getStatus() != null) {
			sql.append(" and status=?");
			args.add(accountInfo.getStatus());
			argTypes.add(-6);// java.sql.Types type
		}
		if (accountInfo.getUpdateTime() != null) {
			sql.append(" and update_time=?");
			args.add(accountInfo.getUpdateTime());
			argTypes.add(93);// java.sql.Types type
		}
		if (accountInfo.getCreateTime() != null) {
			sql.append(" and create_time=?");
			args.add(accountInfo.getCreateTime());
			argTypes.add(93);// java.sql.Types type
		}
	}

	@Override
	public int updatePwd(int id, String newPassword) {
		String sql="update t_account_info set update_time=now(), password=? where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, newPassword, id);
	}

	@Override
	public int lockAccount(int id) {
		String sql="update t_account_info set update_time=now(), status=(status|?) where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, Constants.STATUS_LOCKED, id);
	}
	@Override
	public int unLockAccount(int id) {
		String sql="update t_account_info set update_time=now(), status=(status&~?) where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, Constants.STATUS_LOCKED, id);
	}
	
}
