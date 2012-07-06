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

import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.permission.BaseRoleService;
@Controller
@RequestMapping(value="admin/permission/role")
public class BaseRoleController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private BaseRoleService baseRoleService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/permission/role
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new BaseRole());
		return "/admin/permission/role";
	}
	
	/**
	 * 增加/更新
	 * admin/permission/role/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute BaseRole baseRole, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		baseRoleService.saveOrUpdate(baseRole);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/permission/role/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public BaseRole view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return baseRoleService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/permission/role/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return baseRoleService.batchDelete(ids);
		}
		return baseRoleService.delete(id);
	}
	
	/**
	 * admin/permission/role/query
	 * @param baseRole
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<BaseRole> query(@ModelAttribute BaseRole baseRole, BindingResult result,DataGridModel dgm){
		return baseRoleService.queryByExemple(baseRole, dgm);
	}
}
