package cn.com.carit.market.web.interseptor;

import java.util.Locale;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.com.carit.market.web.Partner;
import cn.com.carit.platform.client.CaritClient;

public class PartnerInterseptor extends HandlerInterceptorAdapter{
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
		if (uri.startsWith("/partner/")) {
			String hl=request.getParameter("hl");
			if ("en".equals(hl)) {
				localeResolver.setDefaultLocale(Locale.US);
			} else {
				localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
				hl=Locale.SIMPLIFIED_CHINESE.toString();
			}
			caritClient.setLocale(hl);
			log.info("caritClient.getLocale="+caritClient.getLocale());
			request.setAttribute("hl", hl);
			if(!"/partner/loginForm".equals(uri) 
					&& !"/partner/login".equals(uri) 
					&& !"/partner/logout".equals(uri)
					&& !"/partner/register".equals(uri)) {
				Partner p=(Partner) request.getSession().getAttribute("partner");
				if (p==null) {
					request.getRequestDispatcher("loginForm").forward(request, response);
					return false;
					
				}
			}
		}
		return super.preHandle(request, response, handler);
	}

}
