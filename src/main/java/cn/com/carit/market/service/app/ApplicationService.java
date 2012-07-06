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
	 * 更新下载次数
	 * @param id
	 * @param downCount
	 * @return
	 */
	int updateDownCount(int id, int downCount);
	
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
	
	/**
	 * 全文检索应用
	 * @param local
	 * @param keywords
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalApplication> fullTextSearch(String local, String keywords, DataGridModel dgm);
	
	/**
	 * 查询用户下载记录
	 * @param local
	 * @param userId
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalApplication> queryUserDownApps(String local, int userId, DataGridModel dgm);
	
	/**
	 * 查询下该应用的用户还下载过哪些应用
	 * @param local
	 * @param appId
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalApplication> queryUserDownReferencedApps(String local, int appId, DataGridModel dgm);
}
