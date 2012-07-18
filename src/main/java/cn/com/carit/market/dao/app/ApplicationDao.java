package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.app.UpdateNotice;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * ApplicationDao
 * Auto generated Code
 */
public interface ApplicationDao {
	
	/**
	 * 增加
	 * @param Application
	 * @return
	 */
	int add(final Application Application);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param PortalApplication
	 * @return
	 */
	int update(Application application);
	
	int updateById(int id);
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
	
	PortalApplication query(int id, String local);
	
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
	 * @param ids
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalApplication> fullTextSearch(String local, String ids, DataGridModel dgm);
	
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
	
	/**
	 * 获取用户下载后更新过的应用信息，给用户发送更新消息时使用
	 * @return
	 */
	List<UpdateNotice> queryUpdatedApplication();
}
