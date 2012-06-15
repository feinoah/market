package cn.com.carit.market.service.impl.app;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppCommentDao;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.service.app.AppCommentService;

/**
 * AppCommentServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppCommentServiceImpl implements AppCommentService{
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private AppCommentDao appCommentDao;
	@Resource
	private ApplicationDao applicationDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(AppComment appComment) {
		if (appComment==null) {
			throw new NullPointerException("appComment object is null...");
		}
		if(appComment.getAppId()==null||appComment.getAppId().intValue()<=0){
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		if (appComment.getId()==0) {
			appCommentDao.add(appComment);
		} else {
			appCommentDao.update(appComment);
		}
		double avg=appCommentDao.queryAvgGrade(appComment.getAppId());
		Application application=new Application();
		application.setId(appComment.getAppId());
		application.setAppLevel((int) Math.rint(avg/2));
		applicationDao.update(application);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appCommentDao.delete(id);
	}
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int deleteByAppId(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appCommentDao.deleteByAppId(appId);
	}

	@Override
	public int[] batchDelete(String[] ids) {
		if (ids==null || ids.length==0) {
			log.warn("id array is empty...");
		}
		return appCommentDao.batchDelete(ids);
	}

	@Override
	public AppComment queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appCommentDao.queryById(id);
	}

	@Override
	public List<AppComment> query() {
		return appCommentDao.query();
	}

	@Override
	public List<AppComment> queryByExemple(AppComment appComment) {
		if (appComment==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCommentDao.queryByExemple(appComment);
	}

	@Override
	public JsonPage<AppComment> queryByExemple(AppComment appComment, DataGridModel dgm) {
		if (appComment==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCommentDao.queryByExemple(appComment, dgm);
	}

	@Override
	public JsonPage<PortalAppComment> queryComment(int appId, DataGridModel dgm) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appCommentDao.queryComment(appId, dgm);
	}

	@Override
	public Map<String,Object> statComment(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appCommentDao.statComment(appId);
	}

	@Override
	public List<Map<String, Object>> statCommentGrade(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appCommentDao.statCommentGrade(appId);
	}
	
}
