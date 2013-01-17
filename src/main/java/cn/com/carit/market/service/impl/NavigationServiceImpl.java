package cn.com.carit.market.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import cn.com.carit.market.dao.NavigationDao;
import cn.com.carit.market.service.NavigationService;
import cn.com.carit.platform.response.NavigationCatalog;

@Service
public class NavigationServiceImpl implements NavigationService {
	
	@Resource
	private NavigationDao dao;

	@Override
	public List<Map<String, Object>> queryEffective() {
		return dao.queryEffective();
	}

	@Override
	public List<Map<String, Object>> queryCatalog() {
		return dao.queryCatalog();
	}

	@Override
	public List<NavigationCatalog> queryResponse() {
		List<Map<String, Object>> catalogs=dao.queryCatalog();
		List<NavigationCatalog> list=new ArrayList<NavigationCatalog>();
		List<Map<String, Object>> navs=dao.queryEffective();
		for (Map<String, Object> catalog : catalogs) {
			Object id=catalog.get("id");
			NavigationCatalog navCatalog = new NavigationCatalog(
					(Integer) id,
					catalog.get("name").toString(), catalog.get("description")
							.toString());
//			List<Map<String, Object>> temp=new ArrayList<Map<String,Object>>();
			for (Map<String, Object> map : navs) {
				if (id.equals(map.get("catalogId"))) {
					Map<String, String> nav=new HashMap<String, String>();
					nav.put("name", map.get("name").toString());
					nav.put("url", map.get("url").toString());
					nav.put("cssClass", map.get("cssClass")==null?"":map.get("cssClass").toString());
					nav.put("logo", map.get("logo")==null?"":map.get("logo").toString());
					nav.put("status", map.get("status").toString());
					navCatalog.getNavs().add(nav);
//				} else {
//					temp.add(map);
				}
			}
//			navs=temp;//改变指向，减少下一次迭代次数
			list.add(navCatalog);
		}
		return list;
	}

	
}
