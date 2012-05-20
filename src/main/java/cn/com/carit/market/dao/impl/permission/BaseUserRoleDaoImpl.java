package cn.com.carit.market.dao.impl.permission;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.bean.BaseUserRole;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.permission.BaseUserRoleDao;
@Repository
public class BaseUserRoleDaoImpl extends BaseDaoImpl  implements
		BaseUserRoleDao {

		@Override
		public int add(BaseUserRole baseUserRole) {
			String sql = "insert into t_base_user_role (user_id, role_id) values (?,?)";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql
					,baseUserRole.getUserId()
					,baseUserRole.getRoleId()
			);
		}

		@Override
		public int[]  bathAdd(final BaseUser baseUser) {
			String sql = "insert into t_base_user_role (user_id, role_id) values (?,?)";
//			final List<Integer> roles=baseUser.getRoles();
			final String [] roles=StringUtils.split(baseUser.getRoles(), ",");
			if(roles==null || roles.length<=0){
				return null;
			}
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, baseUser.getId());
					ps.setInt(2,Integer.parseInt(roles[i])); 
				}
				@Override
				public int getBatchSize() {
					return roles.length;
				}
			});
		}


		@Override
		public int delete(int userId, int roleId) {
			String sql="delete from t_base_user_role where user_id=? and role_id";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, userId, roleId);
		}

		@Override
		public int deleteByUserId(int userId) {
			String sql="delete from t_base_user_role where user_id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, userId);
		}

		@Override
		public int deleteByRoleId(int roleId) {
			String sql="delete from t_base_user_role where role_id=?";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql, roleId);
		}
		
}
