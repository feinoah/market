package cn.com.carit.market.dao.app;

import java.util.List;

import cn.com.carit.market.bean.app.AppDeveloper;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface AppDeveloperDao {
	
	/**
	 * 增加
	 * @param developer
	 * @return
	 */
	int add(AppDeveloper developer);
	
	/**
	 * 更新
	 * @param developer
	 * @return
	 */
	int update(AppDeveloper developer);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDelete(String ids);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AppDeveloper queryById(int id);
	
	/**
	 * 按名字查询
	 * @param name
	 * @return
	 */
	AppDeveloper queryByName(String name);
	
	/**
	 * 条件查询
	 * @param developer
	 * @return
	 */
	List<AppDeveloper> queryByExemple(AppDeveloper developer);
	
	/**
	 * 带分页的条件查询
	 * @param developer
	 * @param dgm
	 * @return
	 */
	JsonPage<AppDeveloper> queryByExemple(AppDeveloper developer, DataGridModel dgm);
	
	/**
	 * 检测是否已存在
	 * @param name
	 * @return
	 */
	int checkExisted(String name);
}
