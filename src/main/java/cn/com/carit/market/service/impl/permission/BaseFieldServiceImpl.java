package cn.com.carit.market.service.impl.permission;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.BaseField;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.permission.BaseFieldDao;
import cn.com.carit.market.service.permission.BaseFieldService;

@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BaseFieldServiceImpl implements BaseFieldService {
	@Resource
	private BaseFieldDao baseFieldDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(BaseField field) {
		if (field==null) {
			throw new NullPointerException("field object is null...");
		}
		if (field.getId()==0) {
			return baseFieldDao.add(field);
		} else {
			return baseFieldDao.update(field);
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return baseFieldDao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return baseFieldDao.batchDelete(ids);
		}
		return 0;
	}

	@Override
	public BaseField queryById(int id) {
		if(id<=0){
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return baseFieldDao.queryById(id);
	}

	
	@Override
	public List<BaseField> queryByField(String filed) {
		return baseFieldDao.queryByField(filed);
	}

	@Override
	public List<Map<String,Object>> queryGroupByField() {
		return baseFieldDao.queryGroupByField();
	}

	@Override
	public List<BaseField> queryByExemple(BaseField field) {
		return baseFieldDao.queryByExemple(field);
	}

	@Override
	public JsonPage<BaseField> queryByExemple(BaseField field, DataGridModel dgm) {
		return baseFieldDao.queryByExemple(field, dgm);
	}

	@Override
	public int checkField(String field) {
		if (!StringUtils.hasText(field)) {
			throw new IllegalArgumentException("field must be not empty...");
		}
		return baseFieldDao.checkField(field);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BaseField> queryByField(String field, int limit) {
		if (!StringUtils.hasText(field)) {
			return Collections.EMPTY_LIST;
		}
		if (limit==0) {
			limit = Constants.PAGE_SIZE;
		}
		return baseFieldDao.queryByField(field, limit);
	}
	
}
