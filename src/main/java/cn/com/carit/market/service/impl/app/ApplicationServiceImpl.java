package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppCommentDao;
import cn.com.carit.market.dao.app.AppVersionFileDao;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * ApplicationServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ApplicationServiceImpl implements ApplicationService{
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private ApplicationDao applicationDao;
	@Resource
	private AppCommentDao appCommentDao;
	@Resource
	private AppVersionFileDao appVersionFileDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(Application application)  {
		if (application == null) {
			throw new NullPointerException("application object is null...");
		}
		int id=application.getId();
		if (id == 0) {
			id=applicationDao.add(application);
		} else {
			Application old=applicationDao.queryById(application.getId());
			if (StringUtils.hasText(application.getIcon())) {
				// 更新了图标
				AttachmentUtil.deleteIcon(old.getIcon());
			}
			
			if (StringUtils.hasText(application.getImages())&&old.getImageList()!=null) {
				// 更新了截图
				for (String path : old.getImageList()) {
					AttachmentUtil.deleteImage(path);
				}
			}
			applicationDao.update(application);
		}
		if (StringUtils.hasText(application.getAppFilePath())) { // 更新文件了
			// 保存版本信息
			AppVersionFile version=new AppVersionFile();
			version.setAppId(id);
			version.setVersion(application.getVersion());
			version.setFilePath(application.getAppFilePath());
			version.setSize(application.getSize());
			version.setNewFeatures(application.getFeatures());
			version.setEnNewFeatures(application.getEnFeatures());
			version.setStatus(application.getStatus());
			appVersionFileDao.add(version);
		}
		return id;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int updateDownCount(int id, int downCount) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		if(downCount<=0){
			log.warn("downCount must be bigger than 0...");
			return 0;
		}
		Application application=new Application();
		application.setId(id);
		application.setDownCount(downCount);
		return applicationDao.update(application);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		Application application=applicationDao.queryById(id);
		if (application!=null) {//删除文件
			AttachmentUtil.deleteIcon(application.getIcon());
			AttachmentUtil.deleteImages(application.getImageList());
			List<AppVersionFile> versionList=appVersionFileDao.queryByAppId(id);
			if (versionList!=null && versionList.size()>0) {
				for (AppVersionFile appVersionFile : versionList) {
					AttachmentUtil.deleteApk(appVersionFile.getFilePath());
				}
			}
		}
		// 删除评论
		appCommentDao.deleteByAppId(id);
		// 删除版本文件
		appVersionFileDao.deleteByAppId(id);
		return applicationDao.delete(id);
	}

	@Override
	public Application queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return applicationDao.queryById(id);
	}

	@Override
	public List<Application> query() {
		return applicationDao.query();
	}

	@Override
	public List<Application> queryByExemple(Application application) {
		if (application==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return applicationDao.queryByExemple(application);
	}

	@Override
	public JsonPage<Application> queryByExemple(Application application, DataGridModel dgm) {
		if (application==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return applicationDao.queryByExemple(application, dgm);
	}

	@Override
	public JsonPage<PortalApplication> queryByExemple(PortalApplication application,
			DataGridModel dgm) {
		return applicationDao.queryByExemple(application, dgm);
	}

	@Override
	public PortalApplication queryAppById(int id, String local) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return applicationDao.query(id, local);
	}

	@Override
	public List<PortalApplication> queryHotFree(String local, int limit) {
		if (limit<=0) {
			throw new IllegalArgumentException("limit must be bigger than 0...");
		}
		return applicationDao.queryHotFree(local, limit);
	}

	@Override
	public List<PortalApplication> queryHotNewFree(String local, int limit) {
		if (limit<=0) {
			throw new IllegalArgumentException("limit must be bigger than 0...");
		}
		return applicationDao.queryHotNewFree(local, limit);
	}

	@Override
	public int checkApplication(String appName, String local) {
		if(StringUtils.hasText(appName)){
			return applicationDao.checkApplication(appName, local);
		}
		return 0;
	}
	
}
