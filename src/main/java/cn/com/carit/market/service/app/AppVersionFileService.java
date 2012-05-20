package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppVersionFile;

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
	 * 按应用Id删除
	 * @param appId
	 * @return
	 */
	int deleteByAppId(int appId);
	
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
	JsonPage queryByExemple(AppVersionFile appVersionFile, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param appVersionFile
	 * @return
	 */
	int getCount(AppVersionFile appVersionFile);
}
