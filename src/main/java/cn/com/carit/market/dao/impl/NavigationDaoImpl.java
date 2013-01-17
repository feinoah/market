package cn.com.carit.market.dao.impl;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import cn.com.carit.market.dao.NavigationDao;
@Repository
public class NavigationDaoImpl extends BaseDaoImpl implements NavigationDao {

	@Override
	public List<Map<String, Object>> queryEffective() {
		String sql="select name, catalog_id catalogId, url, css_class cssClass, logo, status from t_navigation where status>0 order by catalog_id asc, display_index asc";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql);
	}

	@Override
	public List<Map<String, Object>> queryCatalog() {
		String sql="select id, name, description from t_nav_catalog where status>0 order by display_index";
		if (log.isDebugEnabled()) {
			log.debug(String.format("\n%1$s\n", sql));
		}
		return jdbcTemplate.queryForList(sql);
	}

}
