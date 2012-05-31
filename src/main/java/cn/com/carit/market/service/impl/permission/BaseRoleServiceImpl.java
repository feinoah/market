package cn.com.carit.market.service.impl.permission;
import java.util.HashSet;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.permission.BaseRoleDao;
import cn.com.carit.market.dao.permission.BaseRoleModuleDao;
import cn.com.carit.market.dao.permission.BaseUserRoleDao;
import cn.com.carit.market.service.permission.BaseRoleService;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BaseRoleServiceImpl implements BaseRoleService{
	
	@Resource
	private BaseRoleDao baseRoleDao;
	
	@Resource
	private BaseRoleModuleDao baseRoleModuleDao;
	
	@Resource
	private BaseUserRoleDao baseUserRoleDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(BaseRole baseRole) {
		if (baseRole==null) {
			throw new NullPointerException("baseRole object is null...");
		}
		if (baseRole.getId()==0) {
			int id=baseRoleDao.add(baseRole);
			if (id>0 &&StringUtils.hasText( baseRole.getModules())) {
				baseRole.setId(id);
				// 增加模块信息
				baseRoleModuleDao.bathAdd(baseRole);
			} 
		} else {
			// update
			// 删除角色模块信息
			baseRoleModuleDao.deleteByRoleId(baseRole.getId());
			// 更新角色
			baseRoleDao.update(baseRole);
			if (StringUtils.hasText( baseRole.getModules())) {
				// 增加模块信息
				baseRoleModuleDao.bathAdd(baseRole);
			} 
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		// 删除关联的用户信息
		baseUserRoleDao.deleteByRoleId(id);
		// 删除关联的模块信息
		baseRoleModuleDao.deleteByRoleId(id);
		return baseRoleDao.delete(id);
	}

	@Override
	public BaseRole queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		BaseRole role=baseRoleDao.queryById(id);
		if (role!=null) {
			// 查询角色模块
			List<BaseModule> list=baseRoleDao.queryModulesByRoleId(id);
			if (list!=null) {
				// 设置模块信息
				role.setModuleSet(new HashSet<BaseModule>(list));
			}
		}
		return role;
	}

	
	@Override
	public List<BaseRole> queryByUserId(int userId) {
		if (userId<=0) {
			throw new IllegalArgumentException("userId must be bigger than 0...");
		}
		return baseRoleDao.queryByUserId(userId);
	}

	@Override
	public List<BaseRole> query() {
		return baseRoleDao.query();
	}

	@Override
	public List<BaseRole> queryByExemple(BaseRole baseRole) {
		if (baseRole==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return baseRoleDao.queryByExemple(baseRole);
	}

	@Override
	public JsonPage<BaseRole> queryByExemple(BaseRole baseRole, DataGridModel dgm) {
		if (baseRole==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return baseRoleDao.queryByExemple(baseRole, dgm);
	}

}
