package cn.com.carit.market.web.interseptor;

import java.util.Locale;
import java.util.Map;

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
import cn.com.carit.platform.client.ClientUtils;

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
				||"/forget_pwd".equals(uri)
				||"/bluetooth".equals(uri)) {
			String hl=request.getParameter("hl");
			if ("en".equals(hl)) {
				localeResolver.setDefaultLocale(Locale.US);
			} else {
				localeResolver.setDefaultLocale(Locale.SIMPLIFIED_CHINESE);
				hl=Locale.SIMPLIFIED_CHINESE.toString();
			}
			caritClient.setLocale(hl);
			request.setAttribute("hl", hl);
			if(!"/forget_pwd".equals(uri)) {
				AccountInfo accountInfo=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
				if (accountInfo==null) {
					request.getRequestDispatcher("loginForm").forward(request, response);
					return false;
					
				}
				// 获取设备
		        Map<String, String> paramValues=caritClient.buildParamValues("platform.equipment.query", "1.0");
				paramValues.put("accountId", String.valueOf(accountInfo.getId()));
				// 不需要签名的参数放后面
				paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
				String temp=caritClient.getHttpResponse(paramValues);
				if (log.isDebugEnabled()) {
					log.debug("platform.equipment.query 结果: "+temp);
				}
				request.setAttribute(Constants.EQUIPMENT_LIST, temp);
			}
		}
		return super.preHandle(request, response, handler);
	}
	
}
