package cn.com.carit.market.service.permission;
import java.util.List;

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
public interface BaseRoleService {
	/**
	 * 增加
	 * @param baseRole
	 */
	void saveOrUpdate(BaseRole baseRole);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	BaseRole queryById(int id);
	
	/**
	 * 按用户Id查询
	 * @param userId
	 * @return
	 */
	List<BaseRole> queryByUserId(int userId);
	
	/**
	 * 查询
	 * @return
	 */
	List<BaseRole> query();
	
	/**
	 * 条件查询
	 * @param baseRole
	 * @return
	 */
	List<BaseRole> queryByExemple(BaseRole baseRole);
	
	/**
	 * 带分页的条件查询
	 * @param baseRole
	 * @param dgm
	 * @return
	 */
	JsonPage<BaseRole> queryByExemple(BaseRole baseRole, DataGridModel dgm);
	
	/**
	 * 检查角色名称是否存在
	 * @param roleName
	 * @return
	 */
	int checkRoleName(String roleName);
}
