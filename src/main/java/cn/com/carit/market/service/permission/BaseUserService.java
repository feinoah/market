package cn.com.carit.market.service.permission;
import java.util.List;
import java.util.Map;

import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
public interface BaseUserService {
	/**
	 * 增加
	 * @param baseUser
	 * @throws Exception
	 */
	void saveOrUpdate(BaseUser baseUser) throws Exception;
	
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
	BaseUser queryById(int id);
	
	/**
	 * 按邮箱查询
	 * @param email
	 * @return
	 */
	BaseUser queryByEmail(String email);
	
	/**
	 * 查询
	 * @return
	 */
	List<BaseUser> query();
	
	/**
	 * 条件查询
	 * @param baseUser
	 * @return
	 */
	List<BaseUser> queryByExemple(BaseUser baseUser);
	
	/**
	 * 带分页的条件查询
	 * @param baseUser
	 * @param dgm
	 * @return
	 */
	JsonPage<BaseUser> queryByExemple(BaseUser baseUser, DataGridModel dgm);
	
	/**
	 * 用户登录
	 * @param email
	 * @param password
	 * @param ip 登录Ip地址
	 * @return {@link Map} 
	 * 返回的 Map 包含两个key， 一个是key为 answerCode 的响应值，值描述如下：<br>
	 *  <ul>
	 *  <li>-2 用户不存在</li>
	 * 	<li>-1  账号已经停用</li>
	 * 	<li>0	密码错误</li>
	 * 	<li>1	登录成功</li>
	 * 	<li>其它  后台异常</li>
	 * </ul>
	 * 另外一个是一emai为lkey，值为用户{@link BaseUser} 对象。如果响应值不为1，不需要处理该值
	 * @throws Exception
	 */
	Map<String, Object> login(String email, String password, String ip)throws Exception;
	
	/**
	 * 按邮箱Or昵称检测用户是否存在
	 * @param email
	 * @param nickName
	 * @return
	 */
	int checkUser(String email, String nickName);
	
	/**
	 * 修改密码
	 * @param id
	 * @param oldPassword
	 * @param password
	 * @return -3 非法参数（id<=0）；-2账号不存在；-1原始密码错误
	 */
	int updatePassword(int id, String oldPassword, String password) throws Exception;
	
	/**
	 * 更新资料
	 * @param baseUser
	 * @return
	 */
	int updateUser(BaseUser baseUser);
}
