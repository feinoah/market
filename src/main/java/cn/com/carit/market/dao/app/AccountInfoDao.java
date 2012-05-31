package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import  cn.com.carit.market.bean.app.AccountInfo;

/**
 * AccountInfoDao
 * Auto generated Code
 */
public interface AccountInfoDao {
	
	/**
	 * 增加
	 * @param AccountInfo
	 * @return
	 */
	int add(final AccountInfo AccountInfo);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param AccountInfo
	 * @return
	 */
	int update(AccountInfo accountInfo);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	AccountInfo queryById(int id);
	
	/**
	 * 按邮箱查询
	 * @param email
	 * @return
	 */
	AccountInfo queryByEmail(String email);
	
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
