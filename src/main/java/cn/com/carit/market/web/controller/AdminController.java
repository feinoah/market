package cn.com.carit.market.web.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.bean.Tree;
import cn.com.carit.market.bean.TreeMenu;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.service.permission.BaseModuleService;
import cn.com.carit.market.service.permission.BaseRoleService;
import cn.com.carit.market.service.permission.BaseUserService;
/**
 * 后台管理入口类， 不受权限控制
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-5-13
 */
@Controller
public class AdminController {
	
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private BaseUserService baseUserService;
	@Resource
	private BaseModuleService baseModuleService;
	@Resource
	private BaseRoleService baseRoleService;
	
	/**
	 * admin
	 * 跳转到后台首页
	 * @return
	 */
	@RequestMapping(value="admin")
	public String index(){
		return "/admin/index";
	}
	/**
	 * backguide/loginForm
	 * 登录页面
	 * @return
	 */
	@RequestMapping(value="back/loginForm")
	public String loginForm(){
		return "/admin/loginForm";
	}
	
	@RequestMapping(value="back/unauthorized")
	public String unauthorized(){
		return "/admin/unauthorized";
	}
	
	/**
	 * back/login
	 * 用户登录<br>
	 * <ul>
	 * <li>-2  限制登录</li>
	 * <li>-1  用户不存在</li>
	 * <li>0	密码错误</li>
	 * <li>1	登录成功</li>
	 * <li>其它  后台异常</li>
	 * </ul>
	 * @param email
	 * @param password
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="back/login", method=RequestMethod.POST)
	@ResponseBody
	public int login(@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpServletRequest req) throws Exception{
		HttpSession session=req.getSession();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			return -2;
		}
		Map<String, Object> resultMap=baseUserService.login(email, password, req.getRemoteAddr());
		Integer answerCode=(Integer) resultMap.get(Constants.ANSWER_CODE);
		if (answerCode!=null ) {//有响应
			if (answerCode.intValue()==1) {// 登录成功
				// 清除密码错误次数
				BaseUser baseUser=(BaseUser) resultMap.get(email);
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
				session.setAttribute(Constants.ADMIN_USER, baseUser);
				// 得到所有模块
				List<BaseModule> allModule=baseModuleService.queryByUserId(baseUser.getId());
				// 缓存模块，权限拦截时需要使用
				session.setAttribute(Constants.USER_ALL_MOUDLE, allModule);
			}
			if (answerCode.intValue()==0) {// 密码错误
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
			}
		}
		return answerCode;
	}
	
	/**
	 * backguide/logout
	 * @param req
	 */
	@RequestMapping(value="back/logout")
	public String logout(HttpServletRequest req){
		HttpSession session=req.getSession();
		BaseUser baseUser=(BaseUser) session.getAttribute(Constants.ADMIN_USER);
		session.setAttribute(Constants.ADMIN_USER, null);
		session.setAttribute(Constants.USER_ALL_MOUDLE+baseUser.getEmail(), 0);
		return "/admin/loginForm";
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="back/menu/tree", method=RequestMethod.GET)
	@ResponseBody
	public List<Tree>  buildMenuTree(HttpServletRequest req){
		List<BaseModule> moduleList=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		if (moduleList!=null && moduleList.size()>0) {
			List<BaseModule> menuList=new ArrayList<BaseModule>();
			for (BaseModule baseModule : moduleList) {
				if (baseModule.getDisplay()) { // 过滤不显示菜单
					menuList.add(baseModule);
				}
			}
			TreeMenu menu = new TreeMenu(menuList);
			return menu.getTreeJson().getChildren();
		}
		return new ArrayList<Tree>();
	}
	
	/**
	 * 查询所有模块
	 * back/module/query/all
	 * @return
	 */
	@RequestMapping(value="back/module/query/all", method=RequestMethod.GET)
	@ResponseBody
	public List<Tree> queryAllModule(){
		List<BaseModule> menuList= baseModuleService.query();
		List<Tree> tree= new ArrayList<Tree>();
		if (menuList != null && menuList.size() > 0) {
			TreeMenu menu = new TreeMenu(menuList);
			tree.add(menu.getTreeJson());
		}
		return tree;
	}
	
	/**
	 * back/role/query/all
	 * 查询所有角色
	 * @return
	 */
	@RequestMapping(value="back/role/query/all", method=RequestMethod.GET)
	@ResponseBody
	public List<BaseRole> queryAllRole(){
		return baseRoleService.query();
	}
	
	/**
	 * back/module/query/role/{roleId}
	 * 按角色Id查询
	 * @param roleId
	 * @return
	 */
	@RequestMapping(value="back/module/query/role/{roleId}", method=RequestMethod.GET)
	@ResponseBody
	public List<BaseModule> queryByRole(@PathVariable int roleId){
		return baseModuleService.queryByRoleId(roleId);
	}
	
	/**
	 * back/permission/check
	 * 检测编辑权限
	 * @param baseUri
	 * @param req
	 * @return
	 */
	@RequestMapping(value="back/permission/check", method=RequestMethod.GET)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Map<String, Integer> checkEditControl(@RequestParam("baseUri") String baseUri, HttpServletRequest req){
		List<BaseModule> moduleList=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		Map<String, Integer> result=new HashMap<String, Integer>();
		result.put("save", 0);
		result.put("del", 0);
		if (moduleList!=null && moduleList.size()>0) {
			String [] array={"/save","/delete"};
			for (BaseModule baseModule : moduleList) {
				if ((baseUri+array[0]).equals(baseModule.getModuleUrl())) {
					result.put("save", 1);
				}
				if ((baseUri+array[1]).equals(baseModule.getModuleUrl())) {
					result.put("del", 1);
				}
			}
		}
		return result;
	}
	/**
	 * back/permission/account
	 * 检测账号管理权限
	 * @param baseUri
	 * @param req
	 * @return
	 */
	@RequestMapping(value="back/permission/account", method=RequestMethod.GET)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Map<String, Integer> checkAccountControl(@RequestParam("baseUri") String baseUri, HttpServletRequest req){
		List<BaseModule> moduleList=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		Map<String, Integer> result=new HashMap<String, Integer>();
		result.put("save", 0);
		result.put("lock", 0);
		result.put("unlock", 0);
		if (moduleList!=null && moduleList.size()>0) {
			String [] array={"/save","/delete", "/unlock"};
			for (BaseModule baseModule : moduleList) {
				if ((baseUri+array[0]).equals(baseModule.getModuleUrl())) {
					result.put("save", 1);
				}
				if ((baseUri+array[1]).equals(baseModule.getModuleUrl())) {
					result.put("delete", 1);
				}
				if ((baseUri+array[2]).equals(baseModule.getModuleUrl())) {
					result.put("unlock", 1);
				}
			}
		}
		return result;
	}
}
