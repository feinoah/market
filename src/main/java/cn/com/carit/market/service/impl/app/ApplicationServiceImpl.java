package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * ApplicationServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class ApplicationServiceImpl implements ApplicationService{
	
	@Resource
	private ApplicationDao applicationDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(Application application) {
		if (application==null) {
			throw new NullPointerException("application object is null...");
		}
		if (application.getId()==0) {
			return applicationDao.add(application);
		}
		return applicationDao.update(application);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
		}
		return applicationDao.delete(id);
	}

	@Override
	public Application queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
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
	public JsonPage queryByExemple(Application application, DataGridModel dgm) {
		if (application==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return applicationDao.queryByExemple(application, dgm);
	}

	@Override
	public int getCount(Application application) {
		if (application==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return applicationDao.getCount(application);
	}
}
