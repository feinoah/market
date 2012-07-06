package cn.com.carit.market.web.controller.permission;
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
	public int save(@ModelAttribute BaseModule baseModule, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		baseModuleService.saveOrUpdate(baseModule);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/permission/module/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public BaseModule view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return baseModuleService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/permission/module/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return baseModuleService.batchDelete(ids);
		}
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
	public JsonPage<BaseModule> query(@ModelAttribute BaseModule baseModule, BindingResult result, DataGridModel dgm){
		return baseModuleService.queryByExemple(baseModule, dgm);
	}
	
}
