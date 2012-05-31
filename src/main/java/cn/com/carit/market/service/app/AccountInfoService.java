package cn.com.carit.market.service.app;
import java.util.List;
import java.util.Map;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.bean.BaseUser;
import  cn.com.carit.market.bean.app.AccountInfo;

/**
 * AccountInfoService
 * Auto generated Code
 */
public interface AccountInfoService {
	/**
	 * 增加
	 * @param accountInfo
	 * @return
	 */
	void saveOrUpdate(AccountInfo accountInfo) throws Exception;
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AccountInfo queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<AccountInfo> query();
	
	/**
	 * 条件查询
	 * @param accountInfo
	 * @return
	 */
	List<AccountInfo> queryByExemple(AccountInfo accountInfo);
	
	/**
	 * 带分页的条件查询
	 * @param accountInfo
	 * @param dgm
	 * @return
	 */
	JsonPage<AccountInfo> queryByExemple(AccountInfo accountInfo, DataGridModel dgm);
	
	/**
	 * 登录
	 * @param email
	 * @param password
	 * @param ip
	 * @return {@link Map} 
	 * 返回的 Map 包含两个key， 一个是key为 answerCode 的响应值，值描述如下：<br>
	 *  <ul>
	 * <li>-2  帐号被锁定</li>
	 * <li>-1  用户不存在</li>
	 * <li>0	密码错误</li>
	 * <li>1	登录成功</li>
	 * <li>其它  后台异常</li>
	 * </ul>
	 * 另外一个是一emai为lkey，值为用户{@link BaseUser} 对象。如果响应值不为1，不需要处理该值
	 * @throws Exception
	 */
	Map<String, Object> login(String email, String password, String ip) throws Exception;
	
	/**
	 * 修改密码
	 * @param id
	 * @param newPassword
	 * @return
	 */
	int updatePwd(int id, String newPassword);
	
	/**
	 * 锁定账号
	 * @param id
	 * @return
	 */
	int lockAccount(int id);
	
	/**
	 * 解封账号
	 * @param id
	 * @return
	 */
	int unLockAccount(int id);
}
