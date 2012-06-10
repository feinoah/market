package cn.com.carit.market.service.permission;
import java.util.List;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
public interface BaseModuleService {
	/**
	 * 增加
	 * @param baseModule
	 * @return
	 */
	void saveOrUpdate(BaseModule baseModule);
	
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
	BaseModule queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<BaseModule> query();
	
	/**
	 * 条件查询
	 * @param baseModule
	 * @return
	 */
	List<BaseModule> queryByExemple(BaseModule baseModule);
	
	/**
	 * 带分页的条件查询
	 * @param baseModule
	 * @param dgm
	 * @return
	 */
	JsonPage<BaseModule> queryByExemple(BaseModule baseModule, DataGridModel dgm);
	
	/**
	 * 按用户ID查询菜单
	 * @param userId
	 * @return
	 */
	List<BaseModule> queryByUserId(int userId);
	
	/**
	 * 按角色ID查询
	 * @param roleId
	 * @return
	 */
	List<BaseModule> queryByRoleId(int roleId);
	
	/**
	 * 检测模块是否存在
	 * @param moduleName
	 * @return
	 */
	int checkModule(String moduleName);
}
