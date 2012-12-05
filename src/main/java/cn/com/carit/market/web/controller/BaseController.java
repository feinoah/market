package cn.com.carit.market.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


public class BaseController {
	
	protected Logger logger=LoggerFactory.getLogger(getClass());

	/**
	 * 获取session
	 * @return
	 */
	protected HttpSession getSession(){
		return getRequest().getSession();
	}
	
	/**
	 * 获取Request
	 * @return
	 */
	protected HttpServletRequest getRequest(){
		return ((ServletRequestAttributes) RequestContextHolder
				.getRequestAttributes()).getRequest();
	}
	
	/**
	 * 设置属性
	 * @param attributeName
	 * @param attributeValue
	 * @param inSession
	 */
	protected void addAttribute(String attributeName, Object attributeValue, boolean inSession){
		if (inSession) {
			getSession().setAttribute(attributeName, attributeValue);
		} else {
			getRequest().setAttribute(attributeName, attributeValue);
		}
	}
	
}
