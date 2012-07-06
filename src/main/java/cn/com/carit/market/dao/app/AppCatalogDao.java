package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.bean.portal.PortalAppCatalog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AppCatalogDao
 * Auto generated Code
 */
public interface AppCatalogDao {
	
	/**
	 * 增加
	 * @param AppCatalog
	 * @return
	 */
	int add(final AppCatalog AppCatalog);
	
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
	 * 更新
	 * @param PortalAppCatalog
	 * @return
	 */
	int update(AppCatalog appCatalog);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AppCatalog queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AppCatalog> query();
	
	/**
	 * 条件查询
	 * @param appCatalog
	 * @return
	 */
	List<AppCatalog> queryByExemple(AppCatalog appCatalog);
	
	/**
	 * 带分页的条件查询
	 * @param appCatalog
	 * @param dgm
	 * @return
	 */
	JsonPage<AppCatalog> queryByExemple(AppCatalog appCatalog, DataGridModel dgm);
	
	List<PortalAppCatalog> queryAll(String local);
	
	/**
	 * 检测分类是否已经存在
	 * @param name
	 * @param local
	 * @return
	 */
	int checkCatalog(String name, String local);
}
