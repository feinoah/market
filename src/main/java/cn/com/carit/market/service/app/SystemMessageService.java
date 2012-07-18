package cn.com.carit.market.service.app;

import java.util.List;

import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface SystemMessageService {
	/**
	 * 增加/更新
	 * @param systemMessage
	 * @return
	 */
	int saveOrUpdate(SystemMessage systemMessage);
	
	/**
	 * 统一发送消息
	 * @param systemMessage
	 * @return
	 */
	int batchSave(SystemMessage systemMessage);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 批量删除
	 * @param ids
	 * @return
	 */
	int batchDelete(String ids);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	SystemMessage queryById(int id);
	
	
	/**
	 * 查询
	 * @return
	 */
	List<SystemMessage> query();
	
	/**
	 * 条件查询
	 * @param systemMessage
	 * @return
	 */
	List<SystemMessage> queryByExemple(SystemMessage systemMessage);
	
	/**
	 * 带分页的条件查询
	 * @param systemMessage
	 * @param dgm
	 * @return
	 */
	JsonPage<SystemMessage> queryByExemple(SystemMessage systemMessage, DataGridModel dgm);
	
	/**
	 * 按账号查询
	 * @param accountId
	 * @param dgm
	 * @return
	 */
	JsonPage<SystemMessage> queryByAccount(int accountId, DataGridModel dgm);
	
	/**
	 * 按账号查询未读消息
	 * @param accountId
	 * @param dgm
	 * @return
	 */
	JsonPage<SystemMessage> queryUnreadByAccount(int accountId, DataGridModel dgm);
	
	/**
	 * 查看未读消息数量
	 * @param accountId
	 * @return
	 */
	int queryUnreadCountByAccountId(int accountId);
	
	/**
	 * 发送应用更新通知
	 */
	void addAppUpdateNotice();
}
