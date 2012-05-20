package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppAttachment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppAttachmentDao;
import cn.com.carit.market.service.app.AppAttachmentService;

/**
 * AppAttachmentServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppAttachmentServiceImpl implements AppAttachmentService{
	
	@Resource
	private AppAttachmentDao appAttachmentDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(AppAttachment appAttachment) {
		if (appAttachment==null) {
			throw new NullPointerException("appAttachment object is null...");
		}
		if (appAttachment.getId()==0) {
			appAttachmentDao.add(appAttachment);
		} else {
			appAttachmentDao.update(appAttachment);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appAttachmentDao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int deleteByAppId(int appId) {
		if (appId<=0) {
			throw new IllegalArgumentException("appId must be bigger than 0...");
		}
		return appAttachmentDao.deleteByAppId(appId);
	}

	@Override
	public AppAttachment queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appAttachmentDao.queryById(id);
	}

	@Override
	public List<AppAttachment> query() {
		return appAttachmentDao.query();
	}

	@Override
	public List<AppAttachment> queryByExemple(AppAttachment appAttachment) {
		if (appAttachment==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appAttachmentDao.queryByExemple(appAttachment);
	}

	@Override
	public JsonPage queryByExemple(AppAttachment appAttachment, DataGridModel dgm) {
		if (appAttachment==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appAttachmentDao.queryByExemple(appAttachment, dgm);
	}

	@Override
	public int getCount(AppAttachment appAttachment) {
		if (appAttachment==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appAttachmentDao.getCount(appAttachment);
	}
}
