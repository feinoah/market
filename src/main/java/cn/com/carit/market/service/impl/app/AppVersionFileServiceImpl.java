package cn.com.carit.market.service.impl.app;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppVersionFileDao;
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
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(AppVersionFile appVersionFile) {
		if (appVersionFile==null) {
			throw new NullPointerException("appVersionFile object is null...");
		}
		if (appVersionFile.getId()==0) {
			return appVersionFileDao.add(appVersionFile);
		}
		return appVersionFileDao.update(appVersionFile);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
		}
		return appVersionFileDao.delete(id);
	}

	@Override
	public AppVersionFile queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must bigger than 0...");
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
