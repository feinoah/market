package cn.com.carit.market.dao.permission;

import java.util.List;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface BaseModuleDao {
	/**
	 * 增加
	 * @param module
	 * @return
	 */
	int add(BaseModule module);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param module
	 * @return
	 */
	int update(BaseModule module);
	
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
	 * @param module
	 * @return
	 */
	List<BaseModule> queryByExemple(BaseModule module);
	
	/**
	 * 带分页的条件查询
	 * @param module
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(BaseModule module, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param module
	 * @return
	 */
	int getCount(BaseModule module);
	
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
}
