package cn.com.carit.market.dao;

import java.util.List;
import java.util.Map;

public interface NavigationDao {
	/**
	 * 查询有效记录
	 * @return
	 */
	List<Map<String, Object>> queryEffective();
	
	/**
	 * 导航分类类表
	 * @return
	 */
	List<Map<String, Object>> queryCatalog();
}
