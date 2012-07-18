package cn.com.carit.market.dao.app;

import java.util.List;

import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface SystemMessageDao {
	/**
	 * 增加
	 * @param systemMessage
	 * @return
	 */
	int add(SystemMessage systemMessage);
	
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
	 * 更新
	 * @param systemMessage
	 * @return
	 */
	int update(SystemMessage systemMessage);
	
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
	 * 查看未读消息数量
	 * @param accountId
	 * @return
	 */
	int queryUnreadCountByAccountId(int accountId);
	
	/**
	 * 检测消息是否存在（定时任务发送中如果存在一样的未读消息则不再发送）
	 * @param systemMessage
	 * @return
	 */
	int checkSystemMessage(SystemMessage systemMessage);
}
