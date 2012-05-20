package cn.com.carit.market.web.controller.permission;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.permission.BaseModuleService;
@Controller
@RequestMapping(value="admin/permission/module")
public class BaseModuleController {
private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private BaseModuleService baseModuleService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/permission/module/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new BaseModule());
		model.addAttribute("allModule", baseModuleService.query());
		return "admin/permission/module";
	}
	
	/**
	 * 增加/更新
	 * admin/permission/module/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute BaseModule baseModule, BindingResult result, HttpServletRequest req){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		baseModuleService.saveOrUpdate(baseModule);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/permission/module/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public BaseModule view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return baseModuleService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/permission/module/delete/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@PathVariable int id){
		return baseModuleService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/permission/module/query
	 * @param baseModule
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(@ModelAttribute BaseModule baseModule, BindingResult result, DataGridModel dgm){
		return baseModuleService.queryByExemple(baseModule, dgm);
	}
	
}
