package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppCatalog;

/**
 * AppCatalogService
 * Auto generated Code
 */
public interface AppCatalogService {
	/**
	 * 增加
	 * @param appCatalog
	 * @return
	 */
	void saveOrUpdate(AppCatalog appCatalog);
	
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
	JsonPage queryByExemple(AppCatalog appCatalog, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param appCatalog
	 * @return
	 */
	int getCount(AppCatalog appCatalog);
}
