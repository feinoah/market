package cn.com.carit.market.dao.impl.permission;

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

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.permission.BaseUserDao;

@Repository
public class BaseUserDaoImpl extends BaseDaoImpl implements BaseUserDao {
	private final RowMapper<BaseUser> rowMapper = new RowMapper<BaseUser>() {

		@Override
		public BaseUser mapRow(ResultSet rs, int rowNum) throws SQLException {
			BaseUser baseUser = new BaseUser();
			baseUser.setId(rs.getInt("id"));
			baseUser.setEmail(rs.getString("email"));
			baseUser.setPassword(rs.getString("password"));
			baseUser.setNickName(rs.getString("nick_name"));
			baseUser.setRealName(rs.getString("real_name"));
			baseUser.setGender(rs.getByte("gender"));
			baseUser.setUpdateTime(rs.getTimestamp("update_time"));
			baseUser.setCreateTime(rs.getTimestamp("create_time"));
			baseUser.setStatus(rs.getByte("status"));
			baseUser.setRemark(rs.getString("remark"));
			baseUser.setLastLoginIp(rs.getString("last_login_ip"));
			baseUser.setLastLoginTime(rs.getTimestamp("last_login_time"));
			baseUser.setOfficePhone(rs.getString("office_phone"));
			baseUser.setMobile(rs.getLong("mobile"));
			return baseUser;
		}
	};

	@Override
	public int add(final BaseUser baseUser) {
		final String sql = "insert into t_base_user (" + " email"
				+ ", password" + ", nick_name" + ", real_name" + ", gender"
				+ ", update_time" + ", create_time" + ", status" + ", remark"
				+ ", office_phone" + ", mobile" + ") values (" + " ?" + ", ?"
				+ ", ?" + ", ?" + ", ?" + ", now()" + ", now()" + ", ?" + ", ?"
				+ ", ?" + ", ?" + ")";
		log.debug(String.format("\n%1$s\n", sql));
		KeyHolder gkHolder = new GeneratedKeyHolder();
		jdbcTemplate.update(new PreparedStatementCreator() {
			@Override
			public PreparedStatement createPreparedStatement(Connection con)
					throws SQLException {
				PreparedStatement ps = con.prepareStatement(sql,
						Statement.RETURN_GENERATED_KEYS);
				ps.setString(1, baseUser.getEmail());
				ps.setString(2, baseUser.getPassword());
				ps.setString(3, baseUser.getNickName());
				ps.setString(4, baseUser.getRealName());
				ps.setByte(5, baseUser.getGender());
				ps.setByte(6, baseUser.getStatus());
				ps.setString(7, baseUser.getRemark());
				ps.setString(8, baseUser.getOfficePhone());
				ps.setString(9, baseUser.getMobile() == null ? "" : baseUser
						.getMobile().toString());
				return ps;
			}
		}, gkHolder);
		return gkHolder.getKey().intValue();
	}

	@Override
	public int delete(int id) {
		String sql = "delete from t_base_user where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql, id);
	}

	@Override
	public int update(BaseUser baseUser) {
		StringBuilder sql = new StringBuilder(
				"update t_base_user set update_time=now()");
		List<Object> val = new ArrayList<Object>();
		if (StringUtils.hasText(baseUser.getEmail())) {
			sql.append(", email=?");
			val.add(baseUser.getEmail());
		}
		if (StringUtils.hasText(baseUser.getNickName())) {
			sql.append(", nick_name=?");
			val.add(baseUser.getNickName());
		}
		if (StringUtils.hasText(baseUser.getRealName())) {
			sql.append(", real_name=?");
			val.add(baseUser.getRealName());
		}
		if (baseUser.getGender() != null) {
			sql.append(", gender=?");
			val.add(baseUser.getGender());
		}
		if (baseUser.getStatus() != null) {
			sql.append(", status=?");
			val.add(baseUser.getStatus());
		}
		if (StringUtils.hasText(baseUser.getRemark())) {
			sql.append(", remark=?");
			val.add(baseUser.getRemark());
		}
		if (StringUtils.hasText(baseUser.getLastLoginIp())) {
			sql.append(", last_login_ip=?");
			val.add(baseUser.getLastLoginIp());
		}
		if (baseUser.getLastLoginTime() != null) {
			sql.append(", last_login_time=?");
			val.add(baseUser.getLastLoginTime());
		}
		if (StringUtils.hasText(baseUser.getOfficePhone())) {
			sql.append(", office_phone=?");
			val.add(baseUser.getOfficePhone());
		}
		if (baseUser.getMobile() != null) {
			sql.append(", mobile=?");
			val.add(baseUser.getMobile());
		}
		sql.append(" where id=?");
		val.add(baseUser.getId());
		log.debug(String.format("\n%1$s\n", sql));
		return jdbcTemplate.update(sql.toString(), val.toArray());
	}

	@Override
	public BaseUser queryById(int id) {
		String sql = "select * from t_base_user where id=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, id, rowMapper);
	}

	@Override
	public BaseUser queryByEmail(String email) {
		String sql = "select * from t_base_user where email=?";
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql, email, rowMapper);
	}

	@Override
	public List<BaseUser> query() {
		return this.jdbcTemplate.query("select * from t_base_user", rowMapper);
	}

	@Override
	public List<BaseUser> queryByExemple(BaseUser baseUser) {
		StringBuilder sql = new StringBuilder(
				"select * from t_base_user where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		sql.append(buildWhere(args, argTypes, baseUser));
		log.debug(String.format("\n%1$s\n", sql));
		return query(sql.toString(), args, argTypes, rowMapper);
	}

	@Override
	public JsonPage<BaseUser> queryByExemple(BaseUser baseUser, DataGridModel dgm) {
		JsonPage<BaseUser> jsonPage = new JsonPage<BaseUser>(dgm.getPage(), dgm.getRows());
		StringBuilder sql = new StringBuilder(
				"select * from t_base_user where 1=1");
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		String whereSql = buildWhere(args, argTypes, baseUser);
		sql.append(whereSql);
		String countSql = "select count(1) from t_base_user where 1=1"
				+ whereSql;
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

	private String buildWhere(List<Object> args, List<Integer> argTypes,
			BaseUser baseUser) {
		StringBuilder sql = new StringBuilder();
		if (StringUtils.hasText(baseUser.getEmail())) {
			sql.append(" and email  like CONCAT('%',?,'%')");
			args.add(baseUser.getEmail());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(baseUser.getNickName())) {
			sql.append(" and nick_name  like CONCAT('%',?,'%')");
			args.add(baseUser.getNickName());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(baseUser.getRealName())) {
			sql.append(" and real_name  like CONCAT('%',?,'%')");
			args.add(baseUser.getRealName());
			argTypes.add(Types.VARCHAR);
		}
		if (baseUser.getGender() != null) {
			sql.append(" and gender=?");
			args.add(baseUser.getGender());
			argTypes.add(Types.TINYINT);
		}
		if (baseUser.getStatus() != null) {
			sql.append(" and status=?");
			args.add(baseUser.getStatus());
			argTypes.add(Types.TINYINT);
		}
		if (StringUtils.hasText(baseUser.getRemark())) {
			sql.append(" and remark  like CONCAT('%',?,'%')");
			args.add(baseUser.getRemark());
			argTypes.add(Types.VARCHAR);
		}
		if (StringUtils.hasText(baseUser.getLastLoginIp())) {
			sql.append(" and last_login_ip  like CONCAT('%',?,'%')");
			args.add(baseUser.getLastLoginIp());
			argTypes.add(Types.VARCHAR);
		}
		if (baseUser.getLastLoginTimeStart() != null) {
			sql.append(" and last_login_time>=?");
			args.add(baseUser.getLastLoginTimeStart());
			argTypes.add(Types.TIMESTAMP);
		}
		if (baseUser.getLastLoginTimeEnd() != null) {
			sql.append(" and last_login_time<=?");
			args.add(baseUser.getLastLoginTimeEnd());
			argTypes.add(Types.TIMESTAMP);
		}
		if (StringUtils.hasText(baseUser.getOfficePhone())) {
			sql.append(" and office_phone  like CONCAT('%',?,'%')");
			args.add(baseUser.getOfficePhone());
			argTypes.add(Types.VARCHAR);
		}
		if (baseUser.getMobile() != null) {
			sql.append(" and mobile  like CONCAT('%',?,'%')");
			args.add(baseUser.getMobile());
			argTypes.add(Types.BIGINT);
		}
		return sql.toString();
	}

	@Override
	public List<BaseRole> queryRolesByUserId(int userId) {
		String sql = "select a.* from t_base_role a left join t_base_user_role b on a.id=b.role_id where b.user_id=?";
		log.debug(String.format("\n%1$s\n", sql));
		List<BaseRole> list = jdbcTemplate.queryForList(sql,
				new Object[] { userId }, new int[] { Types.INTEGER },
				BaseRole.class);
		return list;
	}

	@Override
	public int checkUser(String email, String nickName) {
		String sql = "select 1 from t_base_user where 1=1";
		List<Object> args = new ArrayList<Object>();
		List<Integer> argTypes = new ArrayList<Integer>();
		if(StringUtils.hasText(email)) {
			sql+=" and email=?";
			args.add(email);
			argTypes.add(Types.VARCHAR);
		}
		if(StringUtils.hasText(nickName)){
			sql+=" and nick_name=?";
			args.add(nickName);
			argTypes.add(Types.VARCHAR);
		}
		log.debug(String.format("\n%1$s\n", sql));
		try {
			return jdbcTemplate.queryForInt(sql, args, argTypes);
		} catch (Exception e) {
			log.warn("not exist record of email["+email+"] or nickName["+nickName+"]");
		}
		return 0;
	}
}
