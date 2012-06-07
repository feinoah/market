package cn.com.carit.market.web.controller.portal;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.com.carit.market.common.Constants;
import cn.com.carit.market.service.app.AppCatalogService;
import cn.com.carit.market.service.app.ApplicationService;

@RequestMapping(value="web")
public class WebContoller {

	@Resource
	private AppCatalogService appCatalogService;
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * WEB端首页 {local}/{limit}
	 * @param limit
	 * @param local
	 * @param model
	 * @return
	 */
	@RequestMapping(value="{local}/{limit}", method=RequestMethod.GET)
	public String index(@PathVariable int limit, @PathVariable String local
			, Model model, HttpServletRequest request){
		model.addAttribute("catalogList", appCatalogService.query());
		model.addAttribute("hotFreeList", applicationService.queryHotFree(local, limit));
		model.addAttribute("hotNewFreeList", applicationService.queryHotNewFree(local, limit));
		model.addAttribute(Constants.PORTAL_USER, request.getSession().getAttribute(Constants.PORTAL_USER));
		return "index";
	}
}
