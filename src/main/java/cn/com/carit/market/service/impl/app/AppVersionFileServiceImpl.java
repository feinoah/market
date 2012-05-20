package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppVersionFileDao;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.service.app.AppVersionFileService;

/**
 * AppVersionFileServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppVersionFileServiceImpl implements AppVersionFileService{
	
	@Resource
	private AppVersionFileDao appVersionFileDao;
	@Resource
	private ApplicationDao applicationDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(AppVersionFile appVersionFile) {
		if (appVersionFile==null) {
			throw new NullPointerException("appVersionFile object is null...");
		}
		if (appVersionFile.getAppId()==null || appVersionFile.getAppId().intValue()<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		if (appVersionFile.getId()==0) {
			appVersionFileDao.add(appVersionFile);
		} else {
			appVersionFileDao.update(appVersionFile);
		}
		// 更新应用
		Application app=new Application();
		app.setId(appVersionFile.getAppId());
		app.setSize(appVersionFile.getSize());
		app.setAppFilePath(appVersionFile.getFilePath());
		app.setVersion(appVersionFile.getVersion());
		applicationDao.update(app);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appVersionFileDao.delete(id);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int deleteByAppId(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appVersionFileDao.deleteByAppId(appId);
	}

	@Override
	public AppVersionFile queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appVersionFileDao.queryById(id);
	}

	@Override
	public List<AppVersionFile> query() {
		return appVersionFileDao.query();
	}

	@Override
	public List<AppVersionFile> queryByExemple(AppVersionFile appVersionFile) {
		if (appVersionFile==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appVersionFileDao.queryByExemple(appVersionFile);
	}

	@Override
	public JsonPage queryByExemple(AppVersionFile appVersionFile, DataGridModel dgm) {
		if (appVersionFile==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appVersionFileDao.queryByExemple(appVersionFile, dgm);
	}

	@Override
	public int getCount(AppVersionFile appVersionFile) {
		if (appVersionFile==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appVersionFileDao.getCount(appVersionFile);
	}
}
