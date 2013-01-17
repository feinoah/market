package cn.com.carit.market.service.impl.app;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.portal.AppDownStat;
import cn.com.carit.market.bean.portal.PortalAppDownloadLog;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.dao.app.AppDownloadLogDao;
import cn.com.carit.market.service.app.AppDownloadLogService;

/**
 * AppDownloadLogServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(readOnly=true)
public class AppDownloadLogServiceImpl implements AppDownloadLogService{
	
	private final Logger logger=Logger.getLogger(getClass());
	
	@Resource
	private AppDownloadLogDao appDownloadLogDao;
	
	@Override
	@Transactional(readOnly=false)
	public void saveOrUpdate(AppDownloadLog appDownloadLog) {
		if (appDownloadLog==null) {
			throw new NullPointerException("appDownloadLog object is null...");
		}
		if (appDownloadLog.getId()==0) {
			appDownloadLogDao.add(appDownloadLog);
		} else {
			appDownloadLogDao.update(appDownloadLog);
		}
	}

	@Override
	@Transactional(readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appDownloadLogDao.delete(id);
	}

	@Transactional(readOnly=false)
	@Override
	public int deleteByAppId(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appDownloadLogDao.deleteByAppId(appId);
	}

	@Override
	public AppDownloadLog queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appDownloadLogDao.queryById(id);
	}

	@Override
	public List<AppDownloadLog> query() {
		return appDownloadLogDao.query();
	}

	@Override
	public List<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog) {
		if (appDownloadLog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appDownloadLogDao.queryByExemple(appDownloadLog);
	}

	@Override
	public JsonPage<AppDownloadLog> queryByExemple(AppDownloadLog appDownloadLog, DataGridModel dgm) {
		if (appDownloadLog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appDownloadLogDao.queryByExemple(appDownloadLog, dgm);
	}

	@Override
	public Map<String, Object> appOneMonthDownTrend(int appId) {
		if (appId<=0) {
			logger.warn("appId must be bigger than 0...");
			return null;
		}
		Calendar calendar=Calendar.getInstance();// 当前时间
		calendar.add(Calendar.DATE, -30);
		Date startDate=calendar.getTime();
		Map<String, Object> resultMap=new HashMap<String, Object>();
		List<AppDownStat> downLogs=appDownloadLogDao.statAppDownlog(appId, startDate);
		List<String> categories=new ArrayList<String>();
		List<Integer> data=new ArrayList<Integer>();
		if (downLogs.size()>=30) {//刚好有30天完整记录
			for (AppDownStat stat : downLogs) {
				categories.add(StringUtil.dateToStr(stat.getDate(), Constants.DATE_SHORT_FORMATTER));
				data.add(stat.getCount());
			}
		} else { // 不够30天的数据
			int count=0;
			for (int i = 0; i < 30; i++) {
				calendar.add(Calendar.DATE, +1);
				String dateStr=StringUtil.dateToStr(calendar.getTime()
						, Constants.DATE_SHORT_FORMATTER);
				categories.add(dateStr);
				for (AppDownStat stat : downLogs) {
					if (dateStr.equals(StringUtil.dateToStr(stat.getDate()
							, Constants.DATE_SHORT_FORMATTER))) {
						count=stat.getCount();
						break;
					} else {
						count=0;
					}
				}
				data.add(count);
			}
		}
		resultMap.put("categories", categories);
		resultMap.put("data", data);
		return resultMap;
	}

	@Override
	public JsonPage<PortalAppDownloadLog> queryUserDownApps(int accountId,
			String local, DataGridModel dgm) {
		PortalAppDownloadLog appDownloadLog=new PortalAppDownloadLog();
		appDownloadLog.setAccountId(accountId);
		appDownloadLog.setLocal(local);
		return appDownloadLogDao.queryByExemple(appDownloadLog, dgm);
	}

}
