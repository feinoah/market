package cn.com.carit.market.web.controller.app;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppCatalogService;

/**
 * AppCatalogController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/catalog")
public class AppCatalogController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppCatalogService appCatalogService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/catalog
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppCatalog());
		return "admin/app/catalog";
	}
	
	/**
	 * 增加/更新
	 * admin/app/catalog/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AppCatalog appCatalog, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		appCatalogService.saveOrUpdate(appCatalog);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/catalog/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public AppCatalog view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appCatalogService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/catalog/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return appCatalogService.batchDelete(ids);
		}
		return appCatalogService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/catalog/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<AppCatalog> query(@ModelAttribute AppCatalog appCatalog, BindingResult result,DataGridModel dgm){
		return appCatalogService.queryByExemple(appCatalog, dgm);
	}
}
