package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
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
		int exceptedId = 0;
		if (appVersionFile.getId()==0) {
			if (appVersionFile.getStatus()!=null 
					&& appVersionFile.getStatus().intValue()==Constants.STATUS_VALID) {
				// 新增/编辑的是有效版本，标记更新应用
				updateApp=true;
			}
			// 加新版本记录
			exceptedId=appVersionFileDao.add(appVersionFile);
		} else {
			exceptedId=appVersionFile.getId();
			if (appVersionFile.getStatus().intValue()==Constants.STATUS_VALID) { // 标记更新应用
				updateApp=true;
			} else {
				AppVersionFile oldRecord = appVersionFileDao.queryById(appVersionFile.getId());
				// 将最后一条有效版本更新为无效，应用更新为无效
				if (oldRecord.getStatus().intValue()==Constants.STATUS_VALID) { 
					app.setStatus(Constants.STATUS_INVALID);
					updateApp=true;
				}
			}
			if (StringUtils.hasText(appVersionFile.getFilePath())) {
				// 更新了应用文件
				AttachmentUtil.deleteApk(appVersionFile.getFilePath());
			}
			// 更新版本记录
			appVersionFileDao.update(appVersionFile);
		}
		if (updateApp) {
			// 将旧版本置为无效
			appVersionFileDao.updateToInvalidByAppId(appVersionFile.getAppId(), exceptedId);
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
					version.getAppId(), id);// 除当前版本外的版本记录
			if(list==null || list.size()==0){// 最后一条版本记录
				applicationDao.delete(version.getAppId());
			} else {
				AppVersionFile prevVersion=list.get(0);
				// 启用该条版本
				prevVersion.setStatus(Constants.STATUS_VALID);
				appVersionFileDao.update(prevVersion);
				Application application=applicationDao.queryById(version.getAppId());
				if (version.getVersion().equals(application.getVersion())) { // 删除的是最新版本
//					Application application=new Application();
					application.setId(prevVersion.getAppId());
					application.setAppFilePath(prevVersion.getFilePath());
					application.setFeatures(prevVersion.getNewFeatures());
					application.setEnFeatures(prevVersion.getEnNewFeatures());
					application.setVersion(prevVersion.getVersion());
					applicationDao.update(application);
				}
//				applicationDao.updateById(version.getAppId());
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
