package cn.com.carit.market.dao.permission;

import java.util.List;

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

public interface BaseUserDao {
	/**
	 * 增加
	 * @param user
	 * @return 自增生成的Id
	 */
	int add(final BaseUser user);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param user
	 * @return
	 */
	int update(BaseUser user);
	
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
	 * @param user
	 * @return
	 */
	List<BaseUser> queryByExemple(BaseUser user);
	
	/**
	 * 带分页的条件查询
	 * @param user
	 * @param dgm
	 * @return
	 */
	JsonPage<BaseUser> queryByExemple(BaseUser user, DataGridModel dgm);
	
	/**
	 * 按用户查看角色
	 * @param userId
	 * @return
	 */
	List<BaseRole> queryRolesByUserId(int userId);
	
	/**
	 * 按邮箱or昵称检测用户是否存在
	 * @param email
	 * @param nickName
	 * @return
	 */
	int checkUser(String email, String nickName);
	
}
