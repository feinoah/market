package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AppVersionFileDao
 * Auto generated Code
 */
public interface AppVersionFileDao {
	
	/**
	 * 增加
	 * @param AppVersionFile
	 * @return
	 */
	int add(final AppVersionFile AppVersionFile);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 按应用Id删除
	 * @param appId
	 * @return
	 */
	int deleteByAppId(int appId);
	
	/**
	 * 更新
	 * @param PortalAppVersionFile
	 * @return
	 */
	int update(AppVersionFile appVersionFile);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AppVersionFile queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AppVersionFile> query();
	
	/**
	 * 条件查询
	 * @param appVersionFile
	 * @return
	 */
	List<AppVersionFile> queryByExemple(AppVersionFile appVersionFile);
	
	/**
	 * 带分页的条件查询
	 * @param appVersionFile
	 * @param dgm
	 * @return
	 */
	JsonPage<AppVersionFile> queryByExemple(AppVersionFile appVersionFile, DataGridModel dgm);
	
	/**
	 * 带分页的条件查询
	 * @param appVersionFile
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalAppVersionFile> queryByExemple(PortalAppVersionFile appVersionFile, DataGridModel dgm);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	PortalAppVersionFile query(int id, String local);
	
}
