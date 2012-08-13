package cn.com.carit.market.web.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.market.bean.BaseField;
import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseRole;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.bean.Tree;
import cn.com.carit.market.bean.TreeMenu;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.MD5Util;
import cn.com.carit.market.service.permission.BaseFieldService;
import cn.com.carit.market.service.permission.BaseModuleService;
import cn.com.carit.market.service.permission.BaseRoleService;
import cn.com.carit.market.service.permission.BaseUserService;
/**
 * 后台管理入口类， 不受权限控制
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-5-13
 */
@Controller
public class AdminController{
	
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private BaseFieldService baseFieldService;
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
	 * 	<li>-3密码错误次数太多，半小时内限制登录</li>
	 * 	<li>-2 用户不存在</li>
	 * 	<li>-1  账号已经停用</li>
	 * 	<li>0	密码错误</li>
	 * 	<li>1	登录成功</li>
	 * 	<li>其它  后台异常</li>
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
			return -3;
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
		if(baseUser!=null){
			session.setAttribute(Constants.ADMIN_USER, null);
			session.setAttribute(Constants.ADMIN_USER+baseUser.getEmail(), 0);
		}
		return "/admin/loginForm";
	}
	/**
	 * 
	 * @param oldPassword
	 * @param password
	 * @param req
	 * @return -4没有登录或登录超时；-3 非法参数（id<=0）；-2账号不存在；-1原始密码错误；1 修改成功 ；其它异常
	 * @throws Exception
	 */
	@RequestMapping(value="back/user/changepwd", method=RequestMethod.POST)
	@ResponseBody
	public int changePassword(@RequestParam("oldPassword")String oldPassword
			, @RequestParam("password")String password, HttpServletRequest req) throws Exception {
		HttpSession session=req.getSession();
		BaseUser baseUser=(BaseUser) session.getAttribute(Constants.ADMIN_USER);
		if (baseUser==null) {//没有登录或登录超时
			return -4;
		}
		return baseUserService.updatePassword(baseUser.getId(), oldPassword, password);
	}
	
	/**
	 * 修改资料
	 * @param baseUser
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value="back/user/update", method=RequestMethod.POST)
	@ResponseBody
	public int updateUser(@ModelAttribute BaseUser baseUser, BindingResult result, HttpServletRequest req){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -3;
		}
		HttpSession session=req.getSession();
		BaseUser sessionUser=(BaseUser) session.getAttribute(Constants.ADMIN_USER);
		if (sessionUser==null) {//没有登录或登录超时
			return -2;
		}
		baseUser.setId(sessionUser.getId());
		try {
			baseUserService.updateUser(baseUser);
			// update session user
			session.setAttribute(Constants.ADMIN_USER, baseUserService.queryByEmail(sessionUser.getEmail()));
			return 1;
		} catch (Exception e) {
			log.error("update baseUser error...", e);
			return -1;
		}
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="back/menu/tree", method=RequestMethod.GET)
	@ResponseBody
	public List<Tree>  buildMenuTree(HttpServletRequest req, HttpServletResponse resp) throws Exception{
		List<BaseModule> moduleList=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		if (moduleList==null) {
			//转到登录页面
			req.getRequestDispatcher("/back/loginForm").forward(req, resp);
			return null;
		}
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
	 * 查询所有模块(树结构)
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
	 * 所有模块
	 * @return
	 */
	@RequestMapping(value="back/module/all", method=RequestMethod.GET)
	@ResponseBody
	public List<BaseModule> allModule(){
		return baseModuleService.query();
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
	 * back/role/query/user/{userId}
	 * 按用户Id查询角色
	 * @return
	 */
	@RequestMapping(value="back/role/query/user/{userId}", method=RequestMethod.GET)
	@ResponseBody
	public List<BaseRole> queryByUser(@PathVariable int userId){
		return baseRoleService.queryByUserId(userId);
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
		result.put("del", 0);
		result.put("lock", 0);
		result.put("unlock", 0);
		if (moduleList!=null && moduleList.size()>0) {
			String [] array={"/save","/delete", "/unlock", "/lock"};
			for (BaseModule baseModule : moduleList) {
				if ((baseUri+array[0]).equals(baseModule.getModuleUrl())) {
					result.put("save", 1);
				}
				if ((baseUri+array[1]).equals(baseModule.getModuleUrl())) {
					result.put("del", 1);
				}
				if ((baseUri+array[2]).equals(baseModule.getModuleUrl())) {
					result.put("unlock", 1);
				}
				if ((baseUri+array[3]).equals(baseModule.getModuleUrl())) {
					result.put("lock", 1);
				}
			}
		}
		return result;
	}
	
	/**
	 * 检测application页面操纵权限
	 * @param req
	 * @return
	 */
	@RequestMapping(value="back/permission/app", method=RequestMethod.GET)
	@SuppressWarnings("unchecked")
	@ResponseBody
	public Map<String, Integer> checkApplicationControl(HttpServletRequest req){
		List<BaseModule> moduleList=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		Map<String, Integer> result=new HashMap<String, Integer>();
		result.put("save", 0);
		result.put("del", 0);
		result.put("searchVersion", 0);
		result.put("addVersion", 0);
		if (moduleList!=null && moduleList.size()>0) {
			String [] array={"/admin/app/application/save","/admin/app/application/delete", "/admin/app/version/query", "/admin/app/version/save"};
			for (BaseModule baseModule : moduleList) {
				if (array[0].equals(baseModule.getModuleUrl())) {
					result.put("save", 1);
				}
				if (array[1].equals(baseModule.getModuleUrl())) {
					result.put("del", 1);
				}
				if (array[2].equals(baseModule.getModuleUrl())) {
					result.put("searchVersion", 1);
				}
				if (array[3].equals(baseModule.getModuleUrl())) {
					result.put("addVersion", 1);
				}
			}
		}
		return result;
	}
	/**
	 * app/attachment/upload?sign=[MD5(MD5(email)+time)]&email=&time=&appName=
	 * <br>
	 * 参数说明
	 * <table>
	 * 	<tr><th>名称</th>描述</th><th>是否必须</th></tr>
	 * 	<tr><td>email</td>当前登录后台账号</td><td>T</td></tr>
	 * 	<tr><td>time</td>当前时间截</td><td>T</td></tr>
	 * 	<tr><td>sign</td>签名，规则为：MD5(MD5(email)+time)</td><td>T</td></tr>
	 * 	<tr><td>appName</td>应用名称</td><td>F</td></tr>
	 * </table>
	 * 上传应用附件
	 * @param sign
	 * @param email
	 * @param time
	 * @param appName
	 * @param request
	 * @param response
	 * @return
	 * <table>
	 * 	<tr><th>名称</th>描述</th>></tr>
	 * 	<tr><td>sign</td>有该属性返回证明签名错误</td></tr>
	 * 	<tr><td>apk</td>该值为1时表示缺少比赛必须的应用文件，其它值为上传成功后应用文件对应的路径</td><</tr>
	 * 	<tr><td>icon</td>icon上传成功后的路径</td></tr>
	 * 	<tr><td>image</td>图片文件上传成功后的路径，多个文件用；分隔</td></tr>
	 * </table>
	 * @throws Exception
	 */
	@RequestMapping(value="app/attachment/upload", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, String> upload(@RequestParam String sign
			, @RequestParam String email, @RequestParam long time,@RequestParam String appName,
			HttpServletRequest request, HttpServletResponse response) throws Exception{
		BaseUser user=(BaseUser) request.getSession().getAttribute(
				Constants.ADMIN_USER);
		if (user==null) { // 没有登录
			log.error("not login...");
			//转到登录页面
			request.getRequestDispatcher("/back/loginForm").forward(request, response);
		}
		Map<String, String> result=new HashMap<String, String>();
		String md5Sign=MD5Util.md5Hex(MD5Util.md5Hex(email)+time);
		if (!md5Sign.equalsIgnoreCase(sign)) {
			log.error(sign+" is not equals with "+md5Sign);
			result.put("sign", "1");
			return result;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile apkMultipartFile = multipartRequest.getFile("icon");
		log.debug("----------------------"+multipartRequest);
		if(apkMultipartFile==null||apkMultipartFile.getOriginalFilename().length()<=0){
			log.debug("apkFile must be not empty ...");
			result.put("apk", "1");
			return result;
		}
		StringBuilder images=new StringBuilder();
    	long radom=System.nanoTime();
    	String suffix = apkMultipartFile.getOriginalFilename().substring(
    			apkMultipartFile.getOriginalFilename().lastIndexOf("."));
    	// 随机文件名
    	String fileName =  appName+"_"+radom+ suffix;// 构建文件名称
    	File apkFile=AttachmentUtil.getApkFile(fileName);
    	apkMultipartFile.transferTo(apkFile);
    	result.put("apk", Constants.BASE_PATH_APK+fileName);
    	//页面控件的文件流
    	MultipartFile multipartFile = multipartRequest.getFile("icon");
    	
        if (multipartFile!=null&&multipartFile.getOriginalFilename().length()>0) {
        	// 获取文件的后缀 
        	suffix = multipartFile.getOriginalFilename().substring(
        			multipartFile.getOriginalFilename().lastIndexOf("."));
        	// 随机文件名
        	fileName =  appName+"_"+radom+ suffix;// 构建文件名称
        	File file = AttachmentUtil.getIconFile(fileName);
    		multipartFile.transferTo(file);
        	result.put("icon", Constants.BASE_PATH_ICON+fileName);
		}
		//页面控件的文件流
        List<MultipartFile> imageFiles = multipartRequest.getFiles("image");
        int i=0;
        for (MultipartFile imageFile : imageFiles) {
        	if (imageFile!=null&&imageFile.getOriginalFilename().length()>0) {
        		fileName = imageFile.getOriginalFilename();  
        		String extName = fileName.substring(fileName.lastIndexOf(".")).toLowerCase();  
        		String lastFileName = appName+"_"+radom+"_"+(i+1)+extName;
        		FileCopyUtils.copy(imageFile.getBytes(),AttachmentUtil.getImageFile(lastFileName)); 
        		if (i<4) {
        			images.append(Constants.BASE_PATH_IMAGE+lastFileName).append(";");
        		} else {
        			images.append(Constants.BASE_PATH_IMAGE+lastFileName);
        		}
			}
    		i++;
		}
        result.put("image", images.toString());
        return result;
	}
	
	/**
	 * back/permission/{type}?name=&nickName=
	 * <br>
	 * 检测是否存在， type=module|role|user， 分别代表检测模块名是否已经存在、角色名是否已经存在和用户Email/昵称是否已经存在
	 * @param type
	 * @param name
	 * @param nickName
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="back/permission/{type}", method=RequestMethod.GET)
	public @ResponseBody int checkExisted(@PathVariable String type, @RequestParam(required=false) String name, @RequestParam(required=false) String nickName) throws Exception{
		/*if ("field".equalsIgnoreCase(type)) {
			return baseFieldService.checkField(name);
		} else*/ if ("module".equalsIgnoreCase(type)) {
			return baseModuleService.checkModule(name);
		} else if ("role".equalsIgnoreCase(type)) {
			return baseRoleService.checkRoleName(name);	
		} else if ("user".equalsIgnoreCase(type)) {
			return baseUserService.checkUser(name, nickName);
		} else {
			throw new Exception("not support type...");
		}
	}
	
	/**
	 * <br>back/query/groupby/field
	 * @param field
	 * @param limit
	 * @return
	 */
	@RequestMapping(value="back/query/groupby/field")
	public @ResponseBody List<Map<String,Object>> queryGroupByField(){
		return baseFieldService.queryGroupByField();
	}
	
	/**
	 * back/field/query/{field}
	 * @param filed
	 * @return
	 */
	@RequestMapping(value="back/field/query/{field}", method=RequestMethod.GET)
	public @ResponseBody List<BaseField> queryBaseFieldByField(@PathVariable String field){
		return baseFieldService.queryByField(field);
	}
}
