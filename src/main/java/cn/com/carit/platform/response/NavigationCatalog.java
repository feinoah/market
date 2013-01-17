package cn.com.carit.platform.response;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NavigationCatalog {
	
	private int id;
	private String name;
	private String description;
	private List<Map<String, String>> navs;

	public NavigationCatalog() {
		super();
		navs=new ArrayList<Map<String,String>>();
	}
	
	public NavigationCatalog(int id, String name, String description) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		navs=new ArrayList<Map<String,String>>();
	}


	public NavigationCatalog(int id, String name, String description,
			List<Map<String, String>> navs) {
		super();
		this.id = id;
		this.name = name;
		this.description = description;
		this.navs = navs;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public List<Map<String, String>> getNavs() {
		return navs;
	}

	public void setNavs(List<Map<String, String>> navs) {
		this.navs = navs;
	}
	
}
