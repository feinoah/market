package cn.com.carit.market.web.controller.permission;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.BaseField;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.permission.BaseFieldService;
/**
 * 系统字典控制器
 * @author Xie Gengcai
 *
 */
@Controller
@RequestMapping(value="admin/permission/field")
public class BaseFieldContoller {
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private BaseFieldService baseFieldService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/permission/field
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new BaseField());
		return "admin/permission/field";
	}
	
	/**
	 * 增加系统字典
	 * admin/permission/field/save
	 * @param baseFile
	 * @param result
	 * @return
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute BaseField baseFile, BindingResult result) {
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return baseFieldService.saveOrUpdate(baseFile);
	}
	
	/**
	 * 查看
	 * admin/permission/field/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public BaseField view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return baseFieldService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/permission/field/delete?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id){
		return baseFieldService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/permission/field/query
	 * @param baseModule
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<BaseField> query(@ModelAttribute BaseField baseFile, BindingResult result, DataGridModel dgm){
		return baseFieldService.queryByExemple(baseFile, dgm);
	}
}
