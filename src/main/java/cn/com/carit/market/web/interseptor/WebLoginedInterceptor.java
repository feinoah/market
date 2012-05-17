package cn.com.carit.market.web.interseptor;

import java.util.Iterator;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.util.UrlPathHelper;

/**
 * 登录后才能访问的路径拦截器
 * @author <a href="mailto:xiegengcai@gmail.com">Ivan Xie</a>
 *
 */
public class WebLoginedInterceptor extends HandlerInterceptorAdapter {
	private UrlPathHelper urlPathHelper = new UrlPathHelper();  
	private PathMatcher pathMatcher = new AntPathMatcher(); 
	/**需要拦截的路径*/
	private Properties groupMappings;

	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String url=urlPathHelper.getLookupPathForRequest(request);
		// 找出资源所需要的权限, 即组名
		String group=lookupGroup(url);
		// 所请求的资源不需要保护
		if (group==null) {
			return true;
		}
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}
	/* * 查看 
	　org.springframework.web.servlet.handler.AbstractUrlHandlerMapping.lookupHandler() 
	　* Ant模式的最长子串匹配法. 
	　*/  
	@SuppressWarnings("rawtypes")
	private String lookupGroup(String url){
		String group=groupMappings.getProperty(url);
		if (group==null) {
			String bestPathMatch=null;
			for (Iterator it = groupMappings.keySet().iterator(); it.hasNext();) {
				String registeredPath = (String) it.next(); 
				if (pathMatcher.match(registeredPath, url) && 
						(bestPathMatch==null || 
						bestPathMatch.length() <= registeredPath.length())) {
					group = groupMappings.getProperty(registeredPath);
					bestPathMatch=registeredPath;
				}
			}
		}
		return group;
	}
}
