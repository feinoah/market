package cn.com.carit.market.service.app;
import java.util.List;
import java.util.Map;

import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.portal.PortalAppDownloadLog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

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
	JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog, DataGridModel dgm);
	
	/**
	 * 统计应用一个月的下载趋势
	 * @param appId
	 * @return map 包含两个key（categories和data），分表代表X轴数据和Y轴数据
	 */
	Map<String, Object> appOneMonthDownTrend(int appId);
	
	/**
	 * 查看用户下载应用记录
	 * @param accountId
	 * @param local
	 * @param dgm
	 * @return
	 */
	JsonPage<PortalAppDownloadLog> queryUserDownApps(int accountId, String local, DataGridModel dgm);
}
