package cn.com.carit.market.service.impl.app;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

import  cn.com.carit.market.dao.app.AppDownloadLogDao;
import  cn.com.carit.market.bean.app.AppDownloadLog;
import  cn.com.carit.market.service.app.AppDownloadLogService;

/**
 * AppDownloadLogServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppDownloadLogServiceImpl implements AppDownloadLogService{
	
	@Resource
	private AppDownloadLogDao appDownloadLogDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(AppDownloadLog appDownloadLog) {
		if (appDownloadLog==null) {
			throw new NullPointerException("appDownloadLog object is null...");
		}
		if (appDownloadLog.getId()==0) {
			return appDownloadLogDao.add(appDownloadLog);
		}
		return appDownloadLogDao.update(appDownloadLog);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
		}
		return appDownloadLogDao.delete(id);
	}

	@Override
	public AppDownloadLog queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
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
	public JsonPage queryByExemple(AppDownloadLog appDownloadLog, DataGridModel dgm) {
		if (appDownloadLog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appDownloadLogDao.queryByExemple(appDownloadLog, dgm);
	}

	@Override
	public int getCount(AppDownloadLog appDownloadLog) {
		if (appDownloadLog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appDownloadLogDao.getCount(appDownloadLog);
	}
}
