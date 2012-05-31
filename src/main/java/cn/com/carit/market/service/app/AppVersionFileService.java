package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;

/**
 * AppVersionFileService
 * Auto generated Code
 */
public interface AppVersionFileService {
	/**
	 * 增加
	 * @param appVersionFile
	 * @return
	 */
	void saveOrUpdate(AppVersionFile appVersionFile);
	
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
