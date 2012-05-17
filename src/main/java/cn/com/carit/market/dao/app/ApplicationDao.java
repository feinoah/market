package cn.com.carit.market.dao.app;
import java.util.List;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * ApplicationDao
 * Auto generated Code
 */
public interface ApplicationDao {
	
	/**
	 * 增加
	 * @param Application
	 * @return
	 */
	int add(final Application Application);
	
	/**
	 * 删除
	 * @param id
	 * @return
	 */
	int delete(int id);
	
	/**
	 * 更新
	 * @param Application
	 * @return
	 */
	int update(Application application);
	
	/**
	 * 按Id查询
	 * @param id
	 * @return
	 */
	Application queryById(int id);
	
	/**
	 * 查询
	 * @return
	 */
	List<Application> query();
	
	/**
	 * 条件查询
	 * @param application
	 * @return
	 */
	List<Application> queryByExemple(Application application);
	
	/**
	 * 带分页的条件查询
	 * @param application
	 * @param dgm
	 * @return
	 */
	JsonPage queryByExemple(Application application, DataGridModel dgm);
	
	/**
	 * 按样例获取总数
	 * @param application
	 * @return
	 */
	int getCount(Application application);
	
}
