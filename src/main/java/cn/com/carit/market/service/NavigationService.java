package cn.com.carit.market.service;

import java.util.List;
import java.util.Map;

import cn.com.carit.platform.response.NavigationCatalog;

public interface NavigationService {
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
	
	List<NavigationCatalog> queryResponse();
}
