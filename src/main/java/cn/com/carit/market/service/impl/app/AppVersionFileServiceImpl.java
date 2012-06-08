package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;
import cn.com.carit.market.common.Constants;
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
		Application app=new Application();
		app.setId(appVersionFile.getAppId());
		app.setSize(appVersionFile.getSize());
		app.setAppFilePath(appVersionFile.getFilePath());
		app.setVersion(appVersionFile.getVersion());
		app.setFeatures(appVersionFile.getNewFeatures());
		app.setEnFeatures(appVersionFile.getEnNewFeatures());
		boolean updateApp=false;
		if (appVersionFile.getStatus()!=null 
				&& appVersionFile.getStatus().intValue()!=Constants.STATUS_INVALID) {
			updateApp=true;
		}
		if (appVersionFile.getId()==0) {
			appVersionFileDao.add(appVersionFile);
		} else {
			appVersionFileDao.update(appVersionFile);
			if (appVersionFile.getStatus()!=null 
					&& appVersionFile.getStatus().intValue()==Constants.STATUS_INVALID) {
				updateApp=true;
				// 获取排除当前记录外id值的记录
				List<AppVersionFile> list=appVersionFileDao.queryByAppIdAndExceptId(
						appVersionFile.getAppId(), appVersionFile.getId());
				if(list!=null && list.size()>0){
					AppVersionFile temp=list.get(0);
					app.setSize(appVersionFile.getSize());
					app.setAppFilePath(temp.getFilePath());
					app.setVersion(temp.getVersion());
					app.setFeatures(temp.getNewFeatures());
					app.setEnFeatures(temp.getEnNewFeatures());
				} else { // 最后一条有效版本
					app.setStatus(Constants.STATUS_INVALID);
				}
			}
		}
		if (updateApp) {
			// 更新应用
			applicationDao.update(app);
			
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		AppVersionFile version=appVersionFileDao.queryById(id);
		int row = appVersionFileDao.delete(id);
		if(row>0){
			List<AppVersionFile> list=appVersionFileDao.queryByAppIdAndExceptId(
					version.getAppId(), version.getId());
			if(list==null || list.size()==0){// 最后一条有效版本
				applicationDao.delete(version.getAppId());
			} else {
				applicationDao.updateById(id);
			}
		}
		return row;
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
	public JsonPage<AppVersionFile> queryByExemple(AppVersionFile appVersionFile, DataGridModel dgm) {
		if (appVersionFile==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appVersionFileDao.queryByExemple(appVersionFile, dgm);
	}

	public JsonPage<PortalAppVersionFile> queryByExemple(PortalAppVersionFile appVersionFile,
			DataGridModel dgm) {
		return appVersionFileDao.queryByExemple(appVersionFile, dgm);
	}

	@Override
	public PortalAppVersionFile query(int id, String local) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appVersionFileDao.query(id, local);
	}
}
