package cn.com.carit.market.web;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.service.NavigationService;
import cn.com.carit.market.service.permission.BaseModuleService;
import cn.com.carit.market.service.permission.BaseUserService;
import cn.com.carit.platform.response.NavigationCatalog;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	private static class CacheHolder {
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	private final BaseUserService baseUserService;
	private final BaseModuleService baseModuleService;
	private final NavigationService navigationService;
	
	private final Map<String, BaseUser> userCache;
	private final Map<Integer, List<BaseModule>> userModuleCache;
	private List<NavigationCatalog> navCatalogs;
	
	private CacheManager() {
		logger.info(" init cache start...");
		WebApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		baseUserService=(BaseUserService) ctx.getBean("baseUserServiceImpl");
		baseModuleService=(BaseModuleService) ctx.getBean("baseModuleServiceImpl");
		navigationService = (NavigationService) ctx.getBean("navigationServiceImpl");
		
		userModuleCache=new ConcurrentHashMap<Integer, List<BaseModule>>();
		userCache=new ConcurrentHashMap<String, BaseUser>();
		
		refreshCache();
		
		logger.info(" init cache end ...");
	}
	
	public static CacheManager getInstance(){
		return CacheHolder.INSTANCE;
	}
	
	/**
	 * 刷新缓存
	 */
	public void refreshCache(){
		refreshUserCache();
		navCatalogs=navigationService.queryResponse();
	}
	
	public void refreshUserCache(){
		List<BaseUser> userList=baseUserService.query();
		userModuleCache.clear();
		for (BaseUser user : userList) {
			userModuleCache.put(user.getId(), baseModuleService.queryByUserId(user.getId()));
			userCache.put(user.getEmail(), user);
		}
	}

	public Map<String, BaseUser> getUserCache() {
		return userCache;
	}

	public Map<Integer, List<BaseModule>> getUserModuleCache() {
		return userModuleCache;
	}

	public List<NavigationCatalog> getNavCatalogs() {
		return navCatalogs;
	}

}
