package cn.com.carit.market.web.controller.app;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * ApplicationController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="app/application")
public class ApplicationController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * app/application/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new Application());
		return "/app/application";
	}
	
	/**
	 * 增加/更新
	 * app/application/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(Application application, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return applicationService.saveOrUpdate(application);
	}
	
	/**
	 * 查看
	 * app/application/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public Application view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return null;
		}
		return applicationService.queryById(id);
	}
	
	/**
	 * 删除
	 * app/application/delete/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return -1;
		}
		return applicationService.delete(id);
	}
	
	/**
	 * 查询
	 * app/application/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(Application application,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
}
