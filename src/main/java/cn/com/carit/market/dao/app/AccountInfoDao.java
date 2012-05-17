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
	JsonPage queryByExemple(AccountInfo accountInfo, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param accountInfo
	 * @return
	 */
	int getCount(AccountInfo accountInfo);
	
}
