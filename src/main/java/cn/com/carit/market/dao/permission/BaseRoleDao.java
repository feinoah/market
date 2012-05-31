package cn.com.carit.market.dao.permission;

import java.util.List;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface BaseRoleDao {
	/**
	 * 增加
	 * @param role
	 * @return 返回自动生成主键
	 */
	int add(final BaseRole role);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param role
	 * @return
	 */
	int update(BaseRole role);
	
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
	 * @param role
	 * @return
	 */
	List<BaseRole> queryByExemple(BaseRole role);
	
	/**
	 * 带分页的条件查询
	 * @param role
	 * @param dgm
	 * @return
	 */
	JsonPage<BaseRole> queryByExemple(BaseRole role, DataGridModel dgm);
	
	/**
	 * 按角色查看模块
	 * @param roleId
	 * @return
	 */
	List<BaseModule> queryModulesByRoleId(int roleId);
}
