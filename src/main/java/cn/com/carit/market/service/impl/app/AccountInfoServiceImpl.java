package cn.com.carit.market.service.impl.app;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.MD5Util;
import cn.com.carit.market.dao.app.AccountInfoDao;
import cn.com.carit.market.service.app.AccountInfoService;

/**
 * AccountInfoServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AccountInfoServiceImpl implements AccountInfoService{
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AccountInfoDao accountInfoDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public AccountInfo saveOrUpdate(AccountInfo accountInfo) throws Exception {
		if (accountInfo==null) {
			throw new NullPointerException("accountInfo object is null...");
		}
		String password=accountInfo.getPassword();
		if (StringUtils.hasText(password)) {
			// 加密密码，加密规则 MD5Util.md5Hex(MD5Util.md5Hex(password){email}disturbStr)
			accountInfo.setPassword(MD5Util.md5Hex(accountInfo.getEmail()+MD5Util.md5Hex(password)+MD5Util.DISTURBSTR));
		}
		if (accountInfo.getId()==0) {
			accountInfoDao.add(accountInfo);
		} else {
			// 更新了头像
			AccountInfo old=accountInfoDao.queryById(accountInfo.getId());
			if (StringUtils.hasText(accountInfo.getPhoto())) {
				AttachmentUtil.deletePhoto(old.getPhoto());
			}
			if (StringUtils.hasText(accountInfo.getThumbPhoto())) {
				AttachmentUtil.deletePhoto(old.getThumbPhoto());
			}
			accountInfoDao.update(accountInfo);
			if (!StringUtils.hasText(accountInfo.getEmail())) {
				accountInfo.setEmail(old.getEmail());//
			}
			if (!StringUtils.hasText(accountInfo.getNickName())) {
				accountInfo.setNickName(old.getNickName());
			}
			if (accountInfo.getGender() == null) {
				accountInfo.setGender(old.getGender());
			}
			if (accountInfo.getBirthday() == null) {
				accountInfo.setBirthday(old.getBirthday());
			}
			if (!StringUtils.hasText(accountInfo.getPhoto())) {
				accountInfo.setPhoto(old.getPhoto());
			}
			if (accountInfo.getBalance() == null) {
				accountInfo.setBalance(old.getBalance());
			}
			if (!StringUtils.hasText(accountInfo.getRealName())) {
				accountInfo.setRealName(old.getRealName());
			}
			if (!StringUtils.hasText(accountInfo.getIdCard())) {
				accountInfo.setIdCard(old.getIdCard());
			}
			if (!StringUtils.hasText(accountInfo.getOfficePhone())) {
				accountInfo.setOfficePhone(old.getOfficePhone());
			}
			if (!StringUtils.hasText(accountInfo.getMobile())) {
				accountInfo.setMobile(old.getMobile());
			}
			if (!StringUtils.hasText(accountInfo.getAddress())) {
				accountInfo.setAddress(old.getAddress());
			}
			if (!StringUtils.hasText(accountInfo.getLastLoginIp())) {
				accountInfo.setLastLoginIp(old.getLastLoginIp());
			}
			if (accountInfo.getLastLoginTime() == null) {
				accountInfo.setLastLoginTime(old.getLastLoginTime());
			}
			if (accountInfo.getStatus() == null) {
				accountInfo.setStatus(old.getStatus());
			}
		}
		return accountInfo;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountInfoDao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return accountInfoDao.batchDelete(ids);
		}
		return 0;
	}

	@Override
	public AccountInfo queryById(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountInfoDao.queryById(id);
	}

	@Override
	public List<AccountInfo> query() {
		return accountInfoDao.query();
	}

	@Override
	public List<AccountInfo> queryByExemple(AccountInfo accountInfo) {
		if (accountInfo==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return accountInfoDao.queryByExemple(accountInfo);
	}

	@Override
	public JsonPage<AccountInfo> queryByExemple(AccountInfo accountInfo, DataGridModel dgm) {
		if (accountInfo==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return accountInfoDao.queryByExemple(accountInfo, dgm);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public Map<String, Object> login(String email, String password, String ip)
			throws Exception {
		if (!StringUtils.hasText(email)) {
			throw new IllegalArgumentException("email must be not empty...");
		}
		if (!StringUtils.hasText(password)) {
			throw new IllegalArgumentException("password must be not empty...");
		}
		Map<String, Object> resultMap=new HashMap<String, Object>();
		AccountInfo accountInfo = accountInfoDao.queryByEmail(email);
		if (accountInfo==null) {
			log.error("User["+email+"] does not exist...");
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		// 密码加密
		password=MD5Util.md5Hex(password);
		// 二次加密
		password=MD5Util.md5Hex(email+password+MD5Util.DISTURBSTR);
		if (!password.equalsIgnoreCase(accountInfo.getPassword())) {
			//密码错误
			resultMap.put(Constants.ANSWER_CODE, 0);
			return resultMap;
		}
		if(accountInfo.getStatus()!=Constants.STATUS_VALID){
			// 帐号没启用
			log.error("User["+email+"]  not Enabled...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		
		// 更新登录时间/IP
		AccountInfo updateAccount=new AccountInfo();
		updateAccount.setId(accountInfo.getId());
		updateAccount.setLastLoginIp(ip);
		updateAccount.setLastLoginTime(Calendar.getInstance().getTime());
		accountInfoDao.update(updateAccount);
		resultMap.put(Constants.ANSWER_CODE, 1);
		resultMap.put(email, accountInfo);
		return resultMap;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int updatePwd(int id, String oldPassword, String newPassword) throws Exception {
		if (!StringUtils.hasText(newPassword)) {
			throw new IllegalArgumentException("newPassword must be not empty...");
		}
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		AccountInfo account=accountInfoDao.queryById(id);
		//加密
		oldPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(oldPassword)+MD5Util.DISTURBSTR);
		if(!oldPassword.equalsIgnoreCase(account.getPassword())){
			//密码错误
			log.error("password is incorrect ....");
			return -1;
		}
		newPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(newPassword)+MD5Util.DISTURBSTR);
		return accountInfoDao.updatePwd(id, newPassword);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int lockAccount(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountInfoDao.lockAccount(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int unLockAccount(int id) {
		if (id<=0) {
			throw new IllegalArgumentException("id must be bigger than 0...");
		}
		return accountInfoDao.unLockAccount(id);
	}

	@Override
	public int checkAccount(String email) {
		if(StringUtils.hasText(email)){
			return accountInfoDao.checkAccount(email);
		}
		return 0;
	}

	@Override
	public int checkNickName(String nickName) {
		if(StringUtils.hasText(nickName)){
			return accountInfoDao.checkNickName(nickName);
		}
		return 0;
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int batchLockAccount(String ids) {
		return accountInfoDao.batchLockAccount(ids);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public int batchUnLockAccount(String ids) {
		return accountInfoDao.batchUnLockAccount(ids);
	}
	
}
