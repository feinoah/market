package cn.com.carit.market.web.controller.portal;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppCatalogService;
import cn.com.carit.market.service.app.ApplicationService;

@Controller
@RequestMapping(value="portal")
public class PortalController {

	@Resource
	private AppCatalogService appCatalogService;
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * 所有应用分类接口
	 * portal/catalog/all
	 * @return json
	 */
	@RequestMapping(value="catalog/all", method=RequestMethod.GET)
	@ResponseBody
	public List<AppCatalog> allCatalog(){
		return appCatalogService.query();
	}
	
	/**
	 * 按条件查询应用
	 * portal/app/query
	 * <br>例如：portal/app/query?page=1&rows=10&appName=?...
	 * @return json
	 */
	@RequestMapping(value="app/query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage queryApps(@ModelAttribute Application application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	
}
