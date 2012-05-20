package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppCatalogDao;
import cn.com.carit.market.service.app.AppCatalogService;

/**
 * AppCatalogServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AppCatalogServiceImpl implements AppCatalogService{
	
	@Resource
	private AppCatalogDao appCatalogDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(AppCatalog appCatalog) {
		if (appCatalog==null) {
			throw new NullPointerException("appCatalog object is null...");
		}
		if (appCatalog.getId()==0) {
			appCatalogDao.add(appCatalog);
		} else {
			appCatalogDao.update(appCatalog);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appCatalogDao.delete(id);
	}

	@Override
	public AppCatalog queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appCatalogDao.queryById(id);
	}

	@Override
	public List<AppCatalog> query() {
		return appCatalogDao.query();
	}

	@Override
	public List<AppCatalog> queryByExemple(AppCatalog appCatalog) {
		if (appCatalog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCatalogDao.queryByExemple(appCatalog);
	}

	@Override
	public JsonPage queryByExemple(AppCatalog appCatalog, DataGridModel dgm) {
		if (appCatalog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCatalogDao.queryByExemple(appCatalog, dgm);
	}

	@Override
	public int getCount(AppCatalog appCatalog) {
		if (appCatalog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCatalogDao.getCount(appCatalog);
	}
}
