package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.bean.portal.PortalAppCatalog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppCatalogDao;
import cn.com.carit.market.service.app.AppCatalogService;

/**
 * AppCatalogServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(readOnly=true)
public class AppCatalogServiceImpl implements AppCatalogService{
	
	@Resource
	private AppCatalogDao appCatalogDao;
	
	@Override
	@Transactional(readOnly=false)
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
	@Transactional(readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return appCatalogDao.delete(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return appCatalogDao.batchDelete(ids);
		}
		return 0;
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
	public JsonPage<AppCatalog> queryByExemple(AppCatalog appCatalog, DataGridModel dgm) {
		if (appCatalog==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return appCatalogDao.queryByExemple(appCatalog, dgm);
	}

	@Override
	public List<PortalAppCatalog> queryAll(String local) {
		return appCatalogDao.queryAll(local);
	}

	@Override
	public int checkCatalog(String name, String local) {
		if (StringUtils.hasText(name)) {
			return appCatalogDao.checkCatalog(name, local);
		}
		return 0;
	}
	
}
