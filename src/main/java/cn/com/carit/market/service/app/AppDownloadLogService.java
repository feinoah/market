package cn.com.carit.market.service.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AppDownloadLog;

/**
 * AppDownloadLogService
 * Auto generated Code
 */
public interface AppDownloadLogService {
	/**
	 * 增加
	 * @param appDownloadLog
	 * @return
	 */
	void saveOrUpdate(AppDownloadLog appDownloadLog);
	
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
	AppDownloadLog queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AppDownloadLog> query();
	
	/**
	 * 条件查询
	 * @param appDownloadLog
	 * @return
	 */
	List<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog);
	
	/**
	 * 带分页的条件查询
	 * @param appDownloadLog
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(AppDownloadLog appDownloadLog, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param appDownloadLog
	 * @return
	 */
	int getCount(AppDownloadLog appDownloadLog);
}
