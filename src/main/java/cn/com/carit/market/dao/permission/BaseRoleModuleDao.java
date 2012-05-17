package cn.com.carit.market.dao.permission;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseRoleModule;

public interface BaseRoleModuleDao {
	
	/**
	 * 增加
	 * @param BaseRoleModule
	 * @return
	 */
	int add(BaseRoleModule BaseRoleModule);
	
	/**
	 * 增加角色时批量增加角色模块信息
	 * @param baseRole
	 * @return
	 */
	int [] bathAdd(final BaseRole baseRole);
	
	/**
	 * 删除
	 * @param roleId
	 * @param moduleId
	 * @return
	 */
	int delete(int roleId, int moduleId);
	
	/**
	 * 按roleId删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(int roleId);
	
	/**
	 * 按模块Id删除
	 * @param moduleId
	 */
	void deleteByModuleId(int moduleId);
}
