package cn.com.carit.market.web.controller.permission;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.permission.BaseUserService;
@Controller
@RequestMapping(value="admin/permission/user")
public class BaseUserController{
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private BaseUserService baseUserService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/permission/user/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new BaseUser());
		return "/admin/permission/user";
	}
	
	/**
	 * 增加/更新
	 * admin/permission/user/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute BaseUser baseUser, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		baseUserService.saveOrUpdate(baseUser);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/permission/user/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public BaseUser view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return baseUserService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/permission/user/delete?id={id}
	 * @param id
	 * @return 
	 * <ul>
	 * 	<li>-2   不能删除当前登录用户</li>
	 * 	<li>-1  非法参数</li>
	 * 	<li>1  操作成功</li>
	 * 	<li>其它值，后台异常</li>
	 * </ul>
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id, HttpServletRequest req){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return -1;
		}
		BaseUser user=(BaseUser) req.getSession().getAttribute(
				Constants.ADMIN_USER);
		if (user!=null && user.getId()==id) {
			return -2;
		}
		return baseUserService.delete(id);
	}
	
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<BaseUser> query(@ModelAttribute BaseUser baseUser, BindingResult result, DataGridModel dgm){
		return baseUserService.queryByExemple(baseUser, dgm);
	}
}
