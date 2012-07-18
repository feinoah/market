package cn.com.carit.market.web.controller.portal;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.app.AppDeveloper;
import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.bean.portal.PortalAccountInfo;
import cn.com.carit.market.bean.portal.PortalAppCatalog;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.bean.portal.PortalAppDownloadLog;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.ImageUtils;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.SphinxUtil;
import cn.com.carit.market.service.app.AccountInfoService;
import cn.com.carit.market.service.app.AppCatalogService;
import cn.com.carit.market.service.app.AppCommentService;
import cn.com.carit.market.service.app.AppDeveloperService;
import cn.com.carit.market.service.app.AppDownloadLogService;
import cn.com.carit.market.service.app.AppVersionFileService;
import cn.com.carit.market.service.app.ApplicationService;
import cn.com.carit.market.service.app.SystemMessageService;

@Controller
@RequestMapping(value="portal")
public class PortalController{
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AccountInfoService accountInfoService;
	
	@Resource
	private AppDownloadLogService appDownloadLogService;
	@Resource
	private AppCatalogService appCatalogService;
	
	@Resource
	private ApplicationService applicationService;
	
	@Resource
	private AppCommentService appCommentService;
	
	@Resource
	private AppVersionFileService appVersionFileService;
	
	@Resource
	private AppDeveloperService appDeveloperService;
	
	@Resource
	private SystemMessageService systemMessageService;
	
	/**
	 * 注册帐号<br>
	 * portal/register
	 * @param accountInfo
	 * @param result
	 * @return Map<String, Integer>
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：错误；-1：账号为空；0：密码为空；1：注册成功；2：帐号已存在</td></tr>
	 * 	<tr><td>accountId</td><td>帐号Id</td></tr>
	 * </table>
	 * @throws Exception 
	 */
	@RequestMapping(value="register", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Integer> register(@ModelAttribute AccountInfo accountInfo
			,BindingResult result, HttpServletRequest req, HttpServletResponse res) throws Exception{
		Map<String, Integer> map=new HashMap<String, Integer>();
		if (result.hasErrors()) {
			log.error(result.hasErrors());
			map.put(Constants.ANSWER_CODE, -2);
			return map;
		}
		if (!StringUtils.hasText(accountInfo.getEmail())) {
			log.error("email can't be empty...");
			map.put(Constants.ANSWER_CODE, -1);
			return map;
		}
		if(checkAccount(accountInfo.getEmail())>0){
			log.error("this account["+accountInfo.getEmail()+"] is existed...");
			map.put(Constants.ANSWER_CODE, 2);
			return map;
		}
		if (!StringUtils.hasText(accountInfo.getPassword())) {
			log.error("password can't be empty...");
			map.put(Constants.ANSWER_CODE, -1);
			return map;
		}
		accountInfoService.saveOrUpdate(accountInfo);
//		cookie.name.user
		Cookie cookie = new Cookie(SphinxUtil.getValue("cookie.name.user"), URLEncoder.encode(accountInfo.getNickName(), Constants.CHARACTER_ENCODING_UTF8));
        cookie.setMaxAge(-1);   
        cookie.setPath("/");   
        res.addCookie(cookie);  
		map.put(Constants.ANSWER_CODE, 1);
		map.put("accountId", accountInfo.getId());
		req.getSession().setAttribute(Constants.PORTAL_USER, accountInfo);
		return map;
	}
	/**
	 * portal/check/account?email={email}<br>
	 * 检测帐号是否已经注册，返回1表示该邮箱已经被注册，0表示未注册
	 * @param email
	 * @return
	 */
	@RequestMapping(value="check/account", method=RequestMethod.GET)
	public @ResponseBody int checkAccount(@RequestParam String email){
		return accountInfoService.checkAccount(email);
	}
	
	/**
	 * portal/check/nickName?nickName={nickName}<br>
	 * 检测帐号是否已经注册，返回1表示该邮箱已经被注册，0表示未注册
	 * @param email
	 * @return
	 */
	@RequestMapping(value="check/nickName", method=RequestMethod.GET)
	public @ResponseBody int checkNickName(@RequestParam String nickName){
		return accountInfoService.checkNickName(nickName);
	}
	
	/**
	 * portal/account/login/check<br>
	 * 检测登录状态
	 * @param req
	 * @return
	 */
	@RequestMapping(value="account/login/check", method=RequestMethod.GET)
	public @ResponseBody PortalAccountInfo checkLogin(HttpServletRequest req, HttpServletResponse res){
		AccountInfo accountInfo=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		PortalAccountInfo portalAccount=null;
		if (accountInfo!=null) {
			portalAccount=new PortalAccountInfo();
			BeanUtils.copyProperties(accountInfo, portalAccount);
		} else {
			Cookie cookie = new Cookie((String) SphinxUtil.getValue("cookie.name.user"), "");
			cookie.setPath("/");   
			cookie.setMaxAge(0); // delete the cookie.   
			res.addCookie(cookie);
		}
		return portalAccount;
	}
	
	/**
	 * 登录 portal/login
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-3：密码错误次数太多，临时限制登录；-2：账号被锁定；-1：账号不存在；0：密码错误；1：登录成功</td></tr>
	 * 	<tr><td>portalUser</td><td>{@link PortalAccountInfo}</td></tr>
	 * </table>
	 * @param email
	 * @param password
	 * @param req
	 * @param res
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpServletRequest req, HttpServletResponse res) throws Exception{
		HttpSession session=req.getSession();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		Map<String,Object> result=new HashMap<String, Object>();
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			result.put(Constants.ANSWER_CODE, -3);
			return result;
		}
		Map<String, Object> resultMap=accountInfoService.login(email, password
				, req.getRemoteAddr());
		Integer answerCode=(Integer) resultMap.get(Constants.ANSWER_CODE);
		result.put(Constants.ANSWER_CODE, answerCode);
		PortalAccountInfo portalAccount=new PortalAccountInfo();
		if (answerCode!=null ) {//有响应
			if (answerCode.intValue()==1) {// 登录成功
				// 清除密码错误次数
				AccountInfo account=(AccountInfo) resultMap.get(email);
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
				session.setAttribute(Constants.PORTAL_USER, account);
				BeanUtils.copyProperties(account, portalAccount);
				result.put(Constants.PORTAL_USER, portalAccount);
//				cookie.name.user
				Cookie cookie = new Cookie(SphinxUtil.getValue("cookie.name.user"), URLEncoder.encode(account.getNickName(), Constants.CHARACTER_ENCODING_UTF8));
	            cookie.setMaxAge(-1);   
	            cookie.setPath("/");   
	            res.addCookie(cookie);  
			}
			if (answerCode.intValue()==0) {// 密码错误
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
			}
		}
		return result;
	}
	
	/**
	 * portal/logout
	 * @param req
	 */
	@RequestMapping(value="logout", method=RequestMethod.GET)
	@ResponseBody
	public int logout(HttpServletRequest req, HttpServletResponse res){
		HttpSession session=req.getSession();
		AccountInfo account=(AccountInfo) session.getAttribute(Constants.PORTAL_USER);
		if (account!=null) {
			session.setAttribute(Constants.PORTAL_USER, null);
			session.setAttribute(Constants.USER_ALL_MOUDLE+account.getEmail(), 0);
		}
		Cookie cookie = new Cookie(SphinxUtil.getValue("cookie.name.user"), "");
		cookie.setMaxAge(0); // delete the cookie.   
        cookie.setPath("/");   
        res.addCookie(cookie); 
		return 1;
	}
	
	/**
	 * 修改密码；请求参数 oldPassword、newPassword<br>
	 * portal/account/changepwd
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-4</td><td>session超时</td></tr>
	 * <tr><td>-3</td><td>密码错误次数太多，半小时内限制修改</td></tr>
	 * 	<tr><td>-2</td><td>参数错误（密码、新密码不能为空）</td></tr>
	 * 	<tr><td>-1</td><td>原密码错误</td></tr>
	 * 	<tr><td>1</td><td>成功</td></tr>
	 * 	<tr><td>其它</td><td>后台异常</td></tr>
	 * </table>
	 * @param oldPassword
	 * @param newPassword
	 * @param req
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="account/changepwd", method=RequestMethod.POST)
	@ResponseBody
	public int changePassword(@RequestParam("oldPassword")String oldPassword
			, @RequestParam("newPassword")String newPassword, HttpServletRequest req) throws Exception {
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return -4;
		}
		HttpSession session=req.getSession();
		String email=account.getEmail();
		Object obj=session.getAttribute(Constants.PASSWORD_ERROR_COUNT+email);
		Integer errorCount= obj==null?0:(Integer)obj;
		if (errorCount!=null && errorCount.intValue()>=Constants.MAX_PWD_ERROR_COUNT) {
			log.error("Limit login:password error count("+errorCount+") >="+Constants.MAX_PWD_ERROR_COUNT);
			return -3;
		}
		if (!StringUtils.hasText(oldPassword)||!StringUtils.hasText(newPassword)) {
			log.error("oldPassword and newPassword can't not be empty...");
			return -2;
		}
		int result=accountInfoService.updatePwd(account.getId(), oldPassword, newPassword);
		if (result==-1) {//原始密码错误
			session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
		} else {
			session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
		}
		return result;
	}
	
	/**
	 * 修改头像；请求参数 file<br>
	 * portal/account/changephoto
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：session超时；-1：文件上传失败；1：成功；其它：后台异常</td></tr>
	 * 	<tr><td>photo</td><td>新头像路径</td></tr>
	 * 	<tr><td>thumbPhoto</td><td>缩略头像路径</td></tr>
	 * </table>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/changephoto", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modifyPhoto(HttpServletRequest request) throws Exception{
		AccountInfo account=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (account==null) {
			//session超时
			log.warn("session time out...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		AccountInfo updateAccount=new AccountInfo();
		updateAccount.setId(account.getId());
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		//页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("file");
        try {
	        if (multipartFile!=null&&multipartFile.getOriginalFilename().length()>0) {
	        	// 获取文件的后缀 
	        	String suffix = multipartFile.getOriginalFilename().substring(
	        			multipartFile.getOriginalFilename().lastIndexOf("."));
	        	// 随机文件名
	        	String prefix =  "user_"+account.getId()+"_"+System.nanoTime();// 构建文件名称
	        	String fileName=prefix+suffix;
	        	File file = AttachmentUtil.getPhotoFile(fileName);
				multipartFile.transferTo(file);
				updateAccount.setPhoto(Constants.BASE_PATH_PHOTOS+fileName);
				// 生成缩略图
				ImageUtils imgUtils=new ImageUtils(file);
				imgUtils.resize(24, 24);
				updateAccount.setThumbPhoto(Constants.BASE_PATH_PHOTOS+prefix+"_thumb"+suffix);
				// 更新session
				account.setPhoto(updateAccount.getPhoto());
				account.setThumbPhoto(updateAccount.getThumbPhoto());
			}
        } catch (IllegalStateException e) {
        	log.error("upload file IllegalStateException...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
        	return resultMap;
        } catch (IOException e) {
        	log.error("upload file IOException...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
        	return resultMap;
        } catch (Exception e) {
        	log.error("upload file Exception...", e);
        	resultMap.put(Constants.ANSWER_CODE, -1);
		}
        accountInfoService.saveOrUpdate(updateAccount);
        resultMap.put("photo", updateAccount.getPhoto());
        resultMap.put("thumbPhoto", updateAccount.getThumbPhoto());
        resultMap.put(Constants.ANSWER_CODE, 1);
    	return resultMap;
	}
	
	/**
	 * 修改资料<br>
	 * portal/account/modify
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：session超时；-1：未知错误；1：成功</td></tr>
	 * 	<tr><td>portalUser</td><td>{@link PortalAccountInfo}</td></tr>
	 * </table>
	 * @param accountInfo
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/modify", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> modify(@ModelAttribute AccountInfo accountInfo, 
			BindingResult result, HttpServletRequest request) throws Exception{
		Map<String,Object> resultMap=new HashMap<String, Object>();
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		HttpSession session=request.getSession();
		AccountInfo account=(AccountInfo) session.getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			resultMap.put(Constants.ANSWER_CODE, -2);
			return resultMap;
		}
		accountInfo.setId(account.getId());
		account=accountInfoService.saveOrUpdate(accountInfo);
		PortalAccountInfo portalAccount=new PortalAccountInfo();
		BeanUtils.copyProperties(account, portalAccount);
		resultMap.put(Constants.PORTAL_USER, portalAccount);
		// 更新session
		session.setAttribute(Constants.PORTAL_USER, account);
		resultMap.put(Constants.ANSWER_CODE, 1);
		return resultMap;
	}
	
	/**
	 * 所有应用分类接口<br>
	 * portal/catalog/all?local=en|cn
	 * @return json
	 */
	@RequestMapping(value="catalog/all", method=RequestMethod.GET)
	@ResponseBody
	public List<PortalAppCatalog> allCatalog(@RequestParam(required=false) String local){
		return appCatalogService.queryAll(local);
	}
	
	/**
	 * 按条件查询应用<br>
	 * portal/app/query
	 * <br>例如：portal/app/query?local=cn&page=1&rows=10&appName=?...<br>
	 * <table>
	 * 	<tr><th>参数名称</th><th>描述</th><th>类型</th><th>是否必须</th></tr>
	 * 	<tr><td>page</td><td>页码</td><td>int</td><td>F</td></tr>
	 * 	<tr><td>rows</td><td>每页显示数</td><td>int</td><td>F</td></tr>
	 * 	<tr><td>PortalApplication</td><td>PortalApplication相关属性参数</td><td>{@link PortalApplication}</td><td>F</td></tr>
	 * </table>
	 * @return json
	 */
	@RequestMapping(value="app/query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<PortalApplication> queryApps(@ModelAttribute PortalApplication application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	/**
	 * 查看App<br>
	 * portal/app/{appId}?local=en|cn
	 * @param appId
	 * @return json {@link PortalApplication}
	 */
	@RequestMapping(value="app/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public PortalApplication viewApp(@PathVariable int appId, @RequestParam(required=false) String local){
		return applicationService.queryAppById(appId, local);
	}
	
	/**
	 * 获取应用的历史版本<br>
	 * portal/app/versions/{appId}?local=en|cn
	 * @param appId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/versions/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<PortalAppVersionFile> queryAppVersions(@PathVariable int appId,  @RequestParam(required=false) String local, DataGridModel dgm) {
		PortalAppVersionFile appVersionFile=new PortalAppVersionFile();
		appVersionFile.setAppId(appId);
		appVersionFile.setLocal(local);
		return appVersionFileService.queryByExemple(appVersionFile, dgm);
	}
	
	/**
	 * 获取应用评论<br>
	 * portal/app/comments/{appId}
	 * @param appId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/comments/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<PortalAppComment> queryAppComments(@PathVariable int appId, DataGridModel dgm) {
		return appCommentService.queryComment(appId, dgm);
	}
	
	/**
	 * 增加评论<br>
	 * portal/app/comment/add
	 * @param appComment
	 * @param result
	 * @param req
	 * @return {@link Map<String,Object>}
	 * <table>
	 * 	<tr><th>属性</th><th>描述</th></tr>
	 * 	<tr><td>answerCode</td><td>-2：没下载过该应用；-1：错误；0：没有登录；1：成功</td></tr>
	 * 	<tr><td>jsonPage</td><td>{@link JsonPage<AppComment>}</td></tr>
	 * </table>
	 */
	@RequestMapping(value="app/comment/add", method=RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> addAppComment(@ModelAttribute AppComment appComment
			, BindingResult result, DataGridModel dgm, HttpServletRequest req){
		Map<String, Object> resultMap=new HashMap<String, Object>();
		if (result.hasErrors()) {
			log.error(result.getAllErrors());
			resultMap.put(Constants.ANSWER_CODE, -1);
			return resultMap;
		}
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			log.error("not login comment ...");
			resultMap.put(Constants.ANSWER_CODE, 0);
			return resultMap;
		}
		appComment.setUserId(account.getId());
		try{
			int answerCode=appCommentService.saveOrUpdate(appComment);
			if (answerCode<0) {
				resultMap.put(Constants.ANSWER_CODE, answerCode);
				return resultMap;
			}
			resultMap.put(Constants.ANSWER_CODE, 1);
			resultMap.put("jsonPage", appCommentService.queryComment(appComment.getAppId(), dgm));
		}catch (Exception e) {
			resultMap.put(Constants.ANSWER_CODE, -1);
			log.error("add app comment error...");
			e.printStackTrace();
		}
		return resultMap;
	}
	
	/**
	 * 下载应用<br>
	 * portal/app/down/{appId}
	 * @param appId
	 * @param req
	 * @throws Exception 
	 */
	@RequestMapping(value="app/down/{appId}", method=RequestMethod.GET)
	public String appDown(@PathVariable int appId,HttpServletRequest req) throws Exception{
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
//			throw new Exception("session time out...");
			return null;
		}
		Application app = applicationService.queryById(appId);
		String fileName=app.getAppFilePath();
		if (!StringUtils.hasText(fileName)) {
			// 文件不存在
			log.warn("can't get app file of app["+appId+"]");
//			throw new Exception("");
			return "redirect:/";
		}
		// 下载次数+1
		applicationService.updateDownCount(appId, app.getDownCount()+1);
		
		AppDownloadLog downlog=new AppDownloadLog();
		downlog.setAccountId(account.getId());
		downlog.setAppId(appId);
		downlog.setVersion(app.getVersion());
		appDownloadLogService.saveOrUpdate(downlog);
		return "redirect:/"+fileName;
	}
	
	/**
	 * portal/app/hot/free/{limit}?local=en|cn<br>
	 * 查询热门免费应用
	 * @param local 区域
	 * @param limit 最大返回记录数
	 * @return
	 */
	@RequestMapping(value="app/hot/free/{limit}", method=RequestMethod.GET)
	@ResponseBody
	public List<PortalApplication> queryHotFree(@PathVariable int limit, @RequestParam(required=false) String local){
		return applicationService.queryHotFree(local, limit);
	}
	
	/**
	 * portal/app/hot/free/new/{limit}?local=en|cn<br>
	 * 查询热门免费应用
	 * @param local 区域
	 * @param limit 最大返回记录数
	 * @return
	 */
	@RequestMapping(value="app/hot/free/new/{limit}", method=RequestMethod.GET)
	@ResponseBody
	public List<PortalApplication> queryHotNewFree(@PathVariable int limit, @RequestParam(required=false) String local){
		return applicationService.queryHotNewFree(local, limit);
	}
	
	/**
	 * 检测是否存在<br>
	 * portal/check/{type}?local=cn|en&name=
	 * <br>type可以是后面的值catalog|app|developer|account，对应type下name分别为分类名称（中/英）、应用名称（中/英）、开发者名称、账号邮箱地址
	 * <br>local 非必须
	 * @param type
	 * @param name
	 * @param local
	 * @return
	 */
	@RequestMapping(value="check/{type}", method=RequestMethod.GET)
	public @ResponseBody int checkExisted(@PathVariable String type, @RequestParam String name, @RequestParam(required=false) String local){
		if ("catalog".equalsIgnoreCase(type)) {
			return appCatalogService.checkCatalog(name, local);
		} else if ("app".equalsIgnoreCase(type)){
			return applicationService.checkApplication(name, local);
		} else if("developer".equalsIgnoreCase(type)) {
			return appDeveloperService.checkExisted(name);
		} else if ("account".equalsIgnoreCase(type)) {
			return accountInfoService.checkAccount(name);
		}
		return 0;
	}
	
	/**
	 * 统计评论级别
	 * <br>portal/app/comment/grade/group/{appId}
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="app/comment/grade/group/{appId}", method=RequestMethod.GET)
	public @ResponseBody List<Map<String, Object>> statCommentGrade(@PathVariable int appId){
		return appCommentService.statCommentGrade(appId);
	}
	
	/**
	 * 获取应用评论的平均值
	 * <br>portal/app/comment/grade/stat/{appId}
	 * @param appId
	 * @return
	 * <table>
	 * 	<tr><th></th><th></th></tr>
	 * </table>
	 */
	@RequestMapping(value="app/comment/grade/stat/{appId}", method=RequestMethod.GET)
	public @ResponseBody Map<String,Object> statComment(@PathVariable int appId){
		return appCommentService.statComment(appId);
	}
	
	/**
	 * 全文搜索应用
	 * <br>portal/app/search?q=&local=cn|en
	 * @param local
	 * @param keywords
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/search", method=RequestMethod.GET)
	public @ResponseBody
	JsonPage<PortalApplication> fullTextSearch(
			@RequestParam(required = false) String local,
			@RequestParam(value = "q") String keywords, DataGridModel dgm) {
		return applicationService.fullTextSearch(local, keywords, dgm);
	}
	
	/**
	 * 应用一个月内的下载趋势
	 * <br>portal/app/month/down/trend/{appId}
	 * <b>返回参数</b>
	 * <table>
	 * 	<tr><th>名称</th><th>描述</th></tr>
	 * 	<tr><td>categories</td><td>X轴数据列表</td></tr>
	 * 	<tr><td>data</td><td>Y轴数据列表</td></tr>
	 * </table>
	 * @param appId
	 * @return
	 */
	@RequestMapping(value="/app/month/down/trend/{appId}")
	public @ResponseBody Map<String, Object> appOneMonthDownTrend(@PathVariable int appId){
		return appDownloadLogService.appOneMonthDownTrend(appId);
	}
	
	/**
	 * 查询用户下载应用记录<br>
	 * portal/app/down/user/{userId}?local=cn|en&page=1&rows=10
	 * @param local
	 * @param userId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="/app/down/user/{userId}", method=RequestMethod.GET)
	public @ResponseBody JsonPage<PortalApplication> queryUserDownApps(@RequestParam(required = false) String local, @PathVariable int userId, DataGridModel dgm){
		return applicationService.queryUserDownApps(local, userId, dgm);
	}
	
	/**
	 * 查询下该应用的用户还下载过哪些应用<br>
	 * portal/app/down/ref/{appId}?local=cn|en
	 * @param local
	 * @param appId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="/app/down/ref/{appId}", method=RequestMethod.GET)
	public @ResponseBody JsonPage<PortalApplication> queryUserDownReferencedApps(@RequestParam(required = false) String local, @PathVariable int appId, DataGridModel dgm){
		return applicationService.queryUserDownReferencedApps(local, appId, dgm);
	}
	
	/**
	 * 查询开发者<br>
	 * portal/app/developer/query
	 * @param developer
	 * @param result
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/developer/query", method=RequestMethod.GET)
	public @ResponseBody JsonPage<AppDeveloper> queryDevelopers(@ModelAttribute AppDeveloper developer, BindingResult result,DataGridModel dgm){
		return appDeveloperService.queryByExemple(developer, dgm);
	}
	
	/**
	 * 用户下载应用记录</br>
	 * portal/app/user/down/{accountId}?local=cn|en&page=1&row=
	 * @param accountId
	 * @param local
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/user/down/{accountId}", method=RequestMethod.GET)
	public @ResponseBody JsonPage<PortalAppDownloadLog> queryUserDownApps(@PathVariable int accountId
			, @RequestParam(required=false) String local, DataGridModel dgm){
		return appDownloadLogService.queryUserDownApps(accountId, local, dgm);
	}
	
	/**
	 * 获取未读消息数目
	 * <pre>
	 * portal/app/user/unread/sysmessage/count/{accountId}
	 * </pre>
	 * @return
	 */
	@RequestMapping(value="app/user/unread/sysmessage/count/{accountId}")
	public @ResponseBody int queryUnreadSystemMessageCountByAccount(@PathVariable int accountId){
		return systemMessageService.queryUnreadCountByAccountId(accountId);
	}
	
	/**
	 * 获取未读消息
	 * <pre>
	 * portal/app/user/unread/sysmessage/{accountId}
	 * </pre>
	 * @param req
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/user/unread/sysmessage/{accountId}", method=RequestMethod.GET)
	public @ResponseBody JsonPage<SystemMessage> queryUnreadSystemMessageByAccount(@PathVariable int accountId, DataGridModel dgm){
		return systemMessageService.queryUnreadByAccount(accountId, dgm);
	}
	
	/**
	 * 获取所有消息
	 * <pre>
	 * portal/app/user/sysmessage/{accountId}
	 * </pre>
	 * @param req
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/user/sysmessage/{accountId}", method=RequestMethod.GET)
	public @ResponseBody JsonPage<SystemMessage> querySystemMessageByAccount(@PathVariable int accountId, DataGridModel dgm){
		SystemMessage systemMessage=new SystemMessage();
		systemMessage.setAccountId(accountId);
		return systemMessageService.queryByExemple(systemMessage, dgm);
	}
	
	/**
	 * 将消息置为已读
	 * <pre>portal/app/user/read/sysmessage/{id}</pre>
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value="app/user/read/sysmessage/{id}", method=RequestMethod.GET)
	public @ResponseBody int markSystemMessageRead(@PathVariable int id,HttpServletRequest req){
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return 0;
		}
		SystemMessage systemMessage=new SystemMessage();
		systemMessage.setStatus(SystemMessage.STATUS_READ);
		systemMessage.setId(id);
		return systemMessageService.saveOrUpdate(systemMessage);
	}
	
	/**
	 * 将消息置为已读
	 * <pre>portal/app/user/delete/sysmessage/{id}</pre>
	 * @param id
	 * @param req
	 * @return
	 */
	@RequestMapping(value="app/user/delete/sysmessage/{id}", method=RequestMethod.GET)
	public @ResponseBody int deleteSystemMessage(@PathVariable int id,HttpServletRequest req){
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return 0;
		}
		return systemMessageService.delete(id);
	}
}
