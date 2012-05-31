package cn.com.carit.market.web.controller.app;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * ApplicationController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/application")
public class ApplicationController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/application/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new Application());
		return "admin/app/application";
	}
	
	/**
	 * 增加/更新
	 * admin/app/application/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute Application application, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		
		if (!StringUtils.hasText(application.getAppName())) {
			log.debug("appName must be not empty ...");
			return -1;
		}
		if (!StringUtils.hasText(application.getEnName())) {
			log.debug("enName must be not empty ...");
			return -1;
		}
		if(!StringUtils.hasText(application.getAppFilePath())){
			log.debug("apkFile must be not empty ...");
			return -1;
		}
		applicationService.saveOrUpdate(application);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/application/view/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view/{id}", method=RequestMethod.GET)
	@ResponseBody
	public Application view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return applicationService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/application/delete/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return -1;
		}
		return applicationService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/application/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<Application> query(@ModelAttribute Application application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	/**
	 * 所有应用
	 * admin/app/application/all
	 * @return
	 */
	@RequestMapping(value="/all", method=RequestMethod.GET)
	@ResponseBody
	public List<Application> allApps(){
		Application application=new Application();
		application.setStatus(Constants.STATUS_VALID);
		return applicationService.queryByExemple(application);
	}
}
