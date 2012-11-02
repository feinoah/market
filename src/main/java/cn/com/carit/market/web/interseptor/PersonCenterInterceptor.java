package cn.com.carit.market.web.interseptor;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.Constants;

public class PersonCenterInterceptor extends HandlerInterceptorAdapter{
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private CookieLocaleResolver localeResolver;
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		if (log.isDebugEnabled()) {
			log.debug("Request for: "+uri);
		}
		if ("/profile".equals(uri)
				||"/location".equals(uri)
				||"/obd".equals(uri)
				||"/poi".equals(uri)) {
			
			String hl=request.getParameter("hl");
			
			if ("en".equals(hl)) {
				localeResolver.setDefaultLocale(Locale.US);
			} else {
				localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
			}
			AccountInfo accountInfo=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
			if (accountInfo==null) {
				request.setAttribute("call_back", uri);
				request.getRequestDispatcher("loginForm").forward(request, response);
				return false;
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
