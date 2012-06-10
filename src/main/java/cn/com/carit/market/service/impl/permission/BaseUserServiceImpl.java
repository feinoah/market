package cn.com.carit.market.service.impl.permission;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.MD5Util;
import cn.com.carit.market.dao.permission.BaseUserDao;
import cn.com.carit.market.dao.permission.BaseUserRoleDao;
import cn.com.carit.market.service.permission.BaseUserService;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class BaseUserServiceImpl implements BaseUserService{
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private BaseUserDao baseUserDao;
	@Resource
	private BaseUserRoleDao baseUserRoleDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(BaseUser baseUser) throws Exception {
		if (baseUser==null) {
			throw new NullPointerException("baseUser object is null...");
		}
		String password=baseUser.getPassword();
		if (StringUtils.hasText(password)) {
			// 加密密码，加密规则 MD5Util.md5Hex(MD5Util.md5Hex(password){email}disturbStr)
			baseUser.setPassword(MD5Util.md5Hex(baseUser.getEmail()+MD5Util.md5Hex(password)+MD5Util.DISTURBSTR));
		}
		if (baseUser.getId()==0) {
			// 增加
			int id=baseUserDao.add(baseUser);
			if (id>0 && StringUtils.hasText(baseUser.getRoles())) {
				baseUser.setId(id);
				// 增加用户角色
				baseUserRoleDao.bathAdd(baseUser);
			}
		} else {
			// update
			// 删除用户角色
			baseUserRoleDao.deleteByUserId(baseUser.getId());
			// 更新用户
			baseUserDao.update(baseUser);
			if ( StringUtils.hasText(baseUser.getRoles())) {
				// 增加用户角色
				baseUserRoleDao.bathAdd(baseUser);
			}
		}
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		// 删除用户角色信息
		baseUserRoleDao.deleteByUserId(id);
		return baseUserDao.delete(id);
	}

	@Override
	public BaseUser queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		BaseUser baseUser=baseUserDao.queryById(id);
		if (baseUser!=null) {
			// 查询用户角色
			List<BaseRole> list=baseUserDao.queryRolesByUserId(id);
			if (list!=null) {
				baseUser.setRoleSet(new HashSet<BaseRole>(list));
			}
		}
		return baseUser;
	}

	@Override
	public BaseUser queryByEmail(String email) {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException("email must be not empty...");
		}
		return baseUserDao.queryByEmail(email);
	}

	@Override
	public List<BaseUser> query() {
		return baseUserDao.query();
	}

	@Override
	public List<BaseUser> queryByExemple(BaseUser baseUser) {
		if (baseUser==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return baseUserDao.queryByExemple(baseUser);
	}

	@Override
	public JsonPage<BaseUser> queryByExemple(BaseUser baseUser, DataGridModel dgm) {
		if (baseUser==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return baseUserDao.queryByExemple(baseUser, dgm);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public Map<String, Object> login(String email, String password, String ip) throws Exception {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException("email must be not empty...");
		}
		if (!StringUtils.hasText(password)) {
			throw new IllegalArgumentException("password must be not empty...");
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		BaseUser baseUser=null;
		baseUser=baseUserDao.queryByEmail(email);
		if (baseUser==null) {
			log.error("User["+email+"] does not exist...");
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		if (!password.equalsIgnoreCase(baseUser.getPassword())) {
			//密码错误
			resultMap.put(Constants.ANSWER_CODE, 0);
			return resultMap;
		}
		// 更新
		BaseUser updateUser=new BaseUser();
		updateUser.setId(baseUser.getId());
		updateUser.setLastLoginIp(ip);
		updateUser.setLastLoginTime(Calendar.getInstance().getTime());
		baseUserDao.update(updateUser);
		resultMap.put(Constants.ANSWER_CODE, 1);
		resultMap.put(email, baseUser);
		return resultMap;
	}

	@Override
	public int checkUser(String email, String nickName) {
		if (!StringUtils.hasText(email) && !StringUtils.hasText(nickName)) {
			throw new IllegalArgumentException("email and nickName must be ont is not empty...");
		}
		return baseUserDao.checkUser(email, nickName);
	}
	
}
