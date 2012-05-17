package cn.com.carit.market.dao.permission;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.bean.BaseUserRole;

public interface BaseUserRoleDao {
	
	/**
	 * 增加
	 * @param BaseUserRole
	 * @return
	 */
	int  add(BaseUserRole BaseUserRole);
	
	/**
	 * 增加用户时批量增加勾选的用户角色信息
	 * @param baseUser
	 * @return
	 */
	 int[]  bathAdd(final BaseUser baseUser);
	
	/**
	 * 删除
	 * @param userId
	 * @param roleId
	 * @return
	 */
	int delete(int userId, int roleId);
	
	/**
	 * 按用户Id删除
	 * @param userId
	 * @return
	 */
	int deleteByUserId(int userId);
	
	/**
	 * 按角色Id删除
	 * @param roleId
	 * @return
	 */
	int deleteByRoleId(int roleId);
}
