package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * ApplicationService
 * Auto generated Code
 */
public interface ApplicationService {
	/**
	 * 增加
	 * @param application
	 * @return
	 */
	int saveOrUpdate(Application application);
	
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
	Application queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<Application> query();
	
	/**
	 * 条件查询
	 * @param application
	 * @return
	 */
	List<Application> queryByExemple(Application application);
	
	/**
	 * 带分页的条件查询
	 * @param application
	 * @param dgm
	 * @return
	 */
	JsonPage<Application> queryByExemple(Application application, DataGridModel dgm);
	
	/**
	 * 带分页的条件查询
	 * @param application
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalApplication> queryByExemple(PortalApplication application, DataGridModel dgm);
	
	PortalApplication queryAppById(int id, String local);
	
	/**
	 * 查询热门免费应用
	 * @param local
	 * @param limit
	 * @return
	 */
	List<PortalApplication> queryHotFree(String local, int limit);
	
	/**
	 * 查询热门免费新品
	 * @param local
	 * @param limit
	 * @return
	 */
	List<PortalApplication> queryHotNewFree(String local, int limit);
	
	/**
	 * 检测应用是否存在
	 * @param appName
	 * @param local
	 * @return
	 */
	int checkApplication(String appName, String local);
}
