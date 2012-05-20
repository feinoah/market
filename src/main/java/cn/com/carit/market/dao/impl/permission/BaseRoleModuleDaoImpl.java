package cn.com.carit.market.dao.impl.permission;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.stereotype.Repository;

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseRoleModule;
import cn.com.carit.market.dao.impl.BaseDaoImpl;
import cn.com.carit.market.dao.permission.BaseRoleModuleDao;
@Repository
public class BaseRoleModuleDaoImpl extends BaseDaoImpl  implements
		BaseRoleModuleDao {
	
		@Override
		public int add(BaseRoleModule baseRoleModule) {
			String sql = "insert into t_base_role_module ( role_id, module_id) values (?,?)";
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.update(sql
					,baseRoleModule.getRoleId()
					,baseRoleModule.getModuleId()
			);
		}
		@Override
		public int[] bathAdd(final BaseRole baseRole) {
			String sql = "insert into t_base_role_module ( role_id, module_id) values (?,?)";
//			final List<Integer> modules=baseRole.getModules();
			final String [] modules=StringUtils.split(baseRole.getModules(), ",");
			if(modules==null||modules.length<=0){
				return null;
			}
			log.debug(String.format("\n%1$s\n", sql));
			return jdbcTemplate.batchUpdate(sql, new BatchPreparedStatementSetter() {
				@Override
				public void setValues(PreparedStatement ps, int i) throws SQLException {
					ps.setInt(1, baseRole.getId());
					ps.setInt(2, Integer.parseInt(modules[i])); 
				}
				@Override
				public int getBatchSize() {
					return modules.length;
				}
			});
		}

		@Override
		public int delete(int roleId, int moduleId) {
			String sql="delete from t_base_role_module where role_id=? and module_id=?";
			log.debug(String.format("\n%1$s\n", sql, moduleId));
			return jdbcTemplate.update(sql, roleId, moduleId);
		}
		@Override
		public int deleteByRoleId(int roleId) {
			String sql="delete from t_base_role_module where role_id=?";
			log.debug(String.format("\n%1$s\n", sql, roleId));
			return jdbcTemplate.update(sql, roleId);
		}
		@Override
		public void deleteByModuleId(int moduleId) {
			String sql="delete from t_base_role_module where module_id=?";
			log.debug(String.format("\n%1$s\n", sql, moduleId));
			 jdbcTemplate.update(sql, moduleId);
		}
		
}
