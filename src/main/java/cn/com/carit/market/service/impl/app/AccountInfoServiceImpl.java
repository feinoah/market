package cn.com.carit.market.service.impl.app;
import java.util.List;
import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

import  cn.com.carit.market.dao.app.AccountInfoDao;
import  cn.com.carit.market.bean.app.AccountInfo;
import  cn.com.carit.market.service.app.AccountInfoService;

/**
 * AccountInfoServiceImpl
 * Auto generated Code
 */
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class AccountInfoServiceImpl implements AccountInfoService{
	
	@Resource
	private AccountInfoDao accountInfoDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public void saveOrUpdate(AccountInfo accountInfo) {
		if (accountInfo==null) {
			throw new NullPointerException("accountInfo object is null...");
		}
		if (accountInfo.getId()==0) {
			accountInfoDao.add(accountInfo);
		} else {
			accountInfoDao.update(accountInfo);
		}
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
	public JsonPage queryByExemple(AccountInfo accountInfo, DataGridModel dgm) {
		if (accountInfo==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return accountInfoDao.queryByExemple(accountInfo, dgm);
	}

	@Override
	public int getCount(AccountInfo accountInfo) {
		if (accountInfo==null) {
			throw new NullPointerException("must given an exemple...");
		}
		return accountInfoDao.getCount(accountInfo);
	}
}
