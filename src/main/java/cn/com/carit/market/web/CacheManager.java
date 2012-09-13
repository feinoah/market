package cn.com.carit.market.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.service.permission.BaseModuleService;
import cn.com.carit.market.service.permission.BaseUserService;

public class CacheManager {
	private final Logger logger=LoggerFactory.getLogger(getClass());
	private static class CacheHolder {
		private static final CacheManager INSTANCE = new CacheManager();
	}
	
	private BaseUserService baseUserService;
	private BaseModuleService baseModuleService;
	
	private Map<String, BaseUser> userCache;
	private Map<Integer, List<BaseModule>> userModuleCache;
	
	private CacheManager() {
		logger.info(" init cache start...");
		WebApplicationContext ctx=ContextLoader.getCurrentWebApplicationContext();
		baseUserService=(BaseUserService) ctx.getBean("baseUserServiceImpl");
		baseModuleService=(BaseModuleService) ctx.getBean("baseModuleServiceImpl");
		
		userModuleCache=new HashMap<Integer, List<BaseModule>>();
		userCache=new HashMap<String, BaseUser>();
		
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

}
