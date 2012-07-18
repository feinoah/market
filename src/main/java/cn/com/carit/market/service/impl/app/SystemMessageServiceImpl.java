package cn.com.carit.market.service.impl.app;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.bean.app.UpdateNotice;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.dao.app.AccountInfoDao;
import cn.com.carit.market.dao.app.ApplicationDao;
import cn.com.carit.market.dao.app.SystemMessageDao;
import cn.com.carit.market.service.app.SystemMessageService;
@Service
@Transactional(propagation=Propagation.SUPPORTS,readOnly=true)
public class SystemMessageServiceImpl implements SystemMessageService {
	private final Logger logger = Logger.getLogger(getClass());
	
	@Resource
	private SystemMessageDao systemMessageDao;
	@Resource
	private ApplicationDao applicationDao;
	@Resource
	private AccountInfoDao accountInfoDao;
	
	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int saveOrUpdate(SystemMessage systemMessage) {
		if (systemMessage==null) {
			logger.error("systemMessage object is null...", new NullPointerException());
			return 0;
		}
		if (systemMessage.getId()>0) {
			return systemMessageDao.update(systemMessage);
		} else {
			return systemMessageDao.add(systemMessage);
		}
	}

	@Override
	public int batchSave(SystemMessage systemMessage) {
		if (systemMessage.getAccountIds()==null) {
			// 给全部账号发送
			List<AccountInfo> list=accountInfoDao.query();
			for (AccountInfo accountInfo : list) {
				SystemMessage sms=new SystemMessage();
				sms.setAccountId(accountInfo.getId());
				// 默认类别
				sms.setCatalog(SystemMessage.CATALOG_SYSTEM_PUSH);
				sms.setContent(systemMessage.getContent());
				sms.setStatus(systemMessage.getStatus());
				sms.setTitle(systemMessage.getTitle());
				saveOrUpdate(sms);
			}
			return list.size();
		}
		for (String accountId : systemMessage.getAccountIds()) {
			SystemMessage sms=new SystemMessage();
			sms.setAccountId(Integer.valueOf(accountId));
			// 默认类别
			sms.setCatalog(SystemMessage.CATALOG_SYSTEM_PUSH);
			sms.setContent(systemMessage.getContent());
			sms.setStatus(systemMessage.getStatus());
			sms.setTitle(systemMessage.getTitle());
			saveOrUpdate(sms);
		}
		return systemMessage.getAccountIds().length;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int delete(int id) {
		if (id<=0) {
			logger.error("id must be bigger than 0...", new IllegalArgumentException());
			return 0;
		}
		return systemMessageDao.delete(id);
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	public int batchDelete(String ids) {
		if (StringUtils.hasText(ids)) {
			return systemMessageDao.batchDelete(ids);
		}
		return 0;
	}

	@Override
	public SystemMessage queryById(int id) {
		if (id<=0) {
			logger.error("id must be bigger than 0...", new IllegalArgumentException());
			return null;
		}
		return systemMessageDao.queryById(id);
	}

	@Override
	public List<SystemMessage> query() {
		return systemMessageDao.query();
	}

	@Override
	public List<SystemMessage> queryByExemple(SystemMessage systemMessage) {
		if (systemMessage==null) {
			logger.error("must be given an exemple...", new NullPointerException());
			return null;
		}
		return systemMessageDao.queryByExemple(systemMessage);
	}

	@Override
	public JsonPage<SystemMessage> queryByExemple(SystemMessage systemMessage,
			DataGridModel dgm) {
		if (systemMessage==null) {
			logger.error("must be given an exemple...", new NullPointerException());
			return new JsonPage<SystemMessage>();
		}
		return systemMessageDao.queryByExemple(systemMessage, dgm);
	}

	@Override
	public JsonPage<SystemMessage> queryByAccount(int accountId,
			DataGridModel dgm) {
		SystemMessage systemMessage=new SystemMessage();
		systemMessage.setAccountId(accountId);
		return systemMessageDao.queryByExemple(systemMessage, dgm);
	}

	@Override
	public JsonPage<SystemMessage> queryUnreadByAccount(int accountId,
			DataGridModel dgm) {
		if (accountId<=0) {
			logger.error("accountId must be bigger than 0...", new IllegalArgumentException());
			return new JsonPage<SystemMessage>();
		}
		SystemMessage systemMessage=new SystemMessage();
		systemMessage.setAccountId(accountId);
		systemMessage.setStatus(SystemMessage.STATUS_UNREAD);
		return systemMessageDao.queryByExemple(systemMessage, dgm);
	}

	@Override
	public int queryUnreadCountByAccountId(int accountId) {
		if (accountId<=0) {
			logger.error("accountId must be bigger than 0...", new IllegalArgumentException());
			return 0;
		}
		return systemMessageDao.queryUnreadCountByAccountId(accountId);
	}

	@Transactional(propagation=Propagation.REQUIRED,readOnly=false)
	@Override
	public void addAppUpdateNotice() {
		// 获取到用户下载应用记录中所有和应用当前版本不一致的列表
		List<UpdateNotice> list=applicationDao.queryUpdatedApplication();
		for (UpdateNotice notice: list) {
			SystemMessage message=new SystemMessage();
			message.setAccountId(notice.getAccountId());
			message.setTitle(notice.getAppName()
					+" "+notice.getAppEnName()+"已经更新");
			// 拼装消息内容：应用Id|应用中文名称|应用英文名称|应用当前版本号
			message.setContent(notice.getAppId()+"|"+notice.getAppName()
					+"|"+notice.getAppEnName()+"|"+notice.getAppVesion());
			if (systemMessageDao.checkSystemMessage(message)==0) {
				message.setCatalog(SystemMessage.CATALOG_APP_UPDATED);
				systemMessageDao.add(message);
			}
		}
	}
}
