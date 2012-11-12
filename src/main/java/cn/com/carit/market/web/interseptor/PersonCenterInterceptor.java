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
import cn.com.carit.platform.client.CaritClient;

public class PersonCenterInterceptor extends HandlerInterceptorAdapter{
	private final Logger log = LoggerFactory.getLogger(getClass());
	@Resource
	private CookieLocaleResolver localeResolver;
	@Resource
	private CaritClient caritClient;
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
				||"/poi".equals(uri)
				||"/forget_pwd".equals(uri)) {
			
			
			if ("en".equals(request.getParameter("hl"))) {
				localeResolver.setDefaultLocale(Locale.US);
				caritClient.setLocale(Locale.US.toString());
			} else {
				localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
				caritClient.setLocale(Locale.SIMPLIFIED_CHINESE.toString());
			}
			if(!"/forget_pwd".equals(uri)) {
				AccountInfo accountInfo=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
				if (accountInfo==null) {
					request.getRequestDispatcher("loginForm").forward(request, response);
					return false;
				}
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
