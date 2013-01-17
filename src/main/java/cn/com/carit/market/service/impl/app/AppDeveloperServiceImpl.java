package cn.com.carit.market.service.impl.app;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AppDeveloper;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AppDeveloperDao;
import cn.com.carit.market.service.app.AppDeveloperService;
@Service
@Transactional(readOnly=true)
public class AppDeveloperServiceImpl implements AppDeveloperService {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppDeveloperDao appDeveloperDao;
	
	@Override
	@Transactional(readOnly=false)
	public int saveOrUpdate(AppDeveloper developer) {
		if (developer==null) {
			log.error("developer object is null..."
					, new NullPointerException());
			return 0;
		}
		if (developer.getId()<=0) {
			return appDeveloperDao.add(developer);
		}
		return appDeveloperDao.update(developer);
	}

	@Override
	@Transactional(readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			log.error("id must be bigger than 0..."
					, new IllegalArgumentException());
			return 0;
		}
		return appDeveloperDao.delete(id);
	}

	@Override
	@Transactional(readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return appDeveloperDao.batchDelete(ids);
		}
		return 0;
	}

	@Override
	public AppDeveloper queryById(int id) {
		if (id<=0) {
			log.error("id must be bigger than 0..."
					, new IllegalArgumentException());
			return null;
		}
		return appDeveloperDao.queryById(id);
	}

	@Override
	public AppDeveloper queryByName(String name) {
		if (!StringUtils.hasText(name)) {
			log.error("name must be not empty..."
					, new IllegalArgumentException());
			return null;
		}
		return appDeveloperDao.queryByName(name);
	}

	@Override
	public List<AppDeveloper> queryByExemple(AppDeveloper developer) {
		return appDeveloperDao.queryByExemple(developer);
	}

	@Override
	public JsonPage<AppDeveloper> queryByExemple(AppDeveloper developer,
			DataGridModel dgm) {
		return appDeveloperDao.queryByExemple(developer, dgm);
	}

	@Override
	public int checkExisted(String name) {
		if (!StringUtils.hasText(name)) {
			log.error("name must be not empty..."
					, new IllegalArgumentException());
			return 0;
		}
		return appDeveloperDao.checkExisted(name);
	}

}
