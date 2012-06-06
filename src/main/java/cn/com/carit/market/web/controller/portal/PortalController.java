package cn.com.carit.market.web.controller.portal;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
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
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalAccountInfo;
import cn.com.carit.market.bean.portal.PortalAppCatalog;
import cn.com.carit.market.bean.portal.PortalAppComment;
import cn.com.carit.market.bean.portal.PortalAppVersionFile;
import cn.com.carit.market.bean.portal.PortalApplication;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.MD5Util;
import cn.com.carit.market.service.app.AccountInfoService;
import cn.com.carit.market.service.app.AppCatalogService;
import cn.com.carit.market.service.app.AppCommentService;
import cn.com.carit.market.service.app.AppDownloadLogService;
import cn.com.carit.market.service.app.AppVersionFileService;
import cn.com.carit.market.service.app.ApplicationService;

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
			,BindingResult result, HttpServletRequest req) throws Exception{
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
	 * portal/account/login/check<br>
	 * 检测登录状态
	 * @param req
	 * @return
	 */
	@RequestMapping(value="account/login/check", method=RequestMethod.GET)
	public @ResponseBody AccountInfo checkLogin(HttpServletRequest req){
		return (AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
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
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="login", method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> login(@RequestParam("email") String email
			, @RequestParam("password") String password
			, HttpServletRequest req) throws Exception{
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
	public int logout(HttpServletRequest req){
		HttpSession session=req.getSession();
		AccountInfo account=(AccountInfo) session.getAttribute(Constants.PORTAL_USER);
		session.setAttribute(Constants.PORTAL_USER, null);
		session.setAttribute(Constants.USER_ALL_MOUDLE+account.getEmail(), 0);
		return 1;
	}
	
	/**
	 * 修改密码；请求参数 oldPassword、newPassword<br>
	 * portal/account/changepwd
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-2</td><td>session超时</td></tr>
	 * 	<tr><td>-1</td><td>参数错误（密码、新密码不能为空）</td></tr>
	 * 	<tr><td>0</td><td>密码错误</td></tr>
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
			return -2;
		}
		if (!StringUtils.hasText(oldPassword)||!StringUtils.hasText(newPassword)) {
			log.error("oldPassword and newPassword can't not be empty...");
			return -1;
		}
		//加密
		oldPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(oldPassword)+MD5Util.DISTURBSTR);
		if(!oldPassword.equalsIgnoreCase(account.getPassword())){
			//密码错误
			log.error("password is incorrect ....");
			return 0;
		}
		newPassword=MD5Util.md5Hex(account.getEmail()+MD5Util.md5Hex(newPassword)+MD5Util.DISTURBSTR);
		return accountInfoService.updatePwd(account.getId(), newPassword);
	}
	
	/**
	 * 修改头像；请求参数 file<br>
	 * portal/account/changephoto
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-2</td><td>session超时</td></tr>
	 * 	<tr><td>-1</td><td>文件上传失败</td></tr>
	 * 	<tr><td>1</td><td>成功</td></tr>
	 * 	<tr><td>其它</td><td>后台异常</td></tr>
	 * </table>
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/changephoto", method=RequestMethod.POST)
	@ResponseBody
	public int modifyPhoto(HttpServletRequest request) throws Exception{
		AccountInfo account=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return -2;
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
	        	String fileName =  "user_"+account+"_"
	        	+System.nanoTime() + suffix;// 构建文件名称
	        	File file = AttachmentUtil.getPhotoFile(fileName);
					multipartFile.transferTo(file);
					updateAccount.setPhoto(Constants.BASE_PATH_PHOTOS+fileName);
			}
        } catch (IllegalStateException e) {
        	log.error("upload file error..."+e.getMessage());
        	return -1;
        } catch (IOException e) {
        	log.error("upload file error..."+e.getMessage());
        	return -1;
        }
        accountInfoService.saveOrUpdate(updateAccount);
		return 1;
	}
	
	/**
	 * 修改资料<br>
	 * portal/account/modify
	 * @param accountInfo
	 * @param result
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="account/modify", method=RequestMethod.POST)
	@ResponseBody
	public int modify(@ModelAttribute AccountInfo accountInfo, 
			BindingResult result, HttpServletRequest request) throws Exception{
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		AccountInfo account=(AccountInfo) request.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
			return -2;
		}
		accountInfo.setId(account.getId());
		accountInfoService.saveOrUpdate(accountInfo);
		return 1;
	}
	
	/**
	 * 所有应用分类接口<br>
	 * portal/catalog/all?local=en|cn
	 * @return json
	 */
	@RequestMapping(value="catalog/all", method=RequestMethod.GET)
	@ResponseBody
	public List<PortalAppCatalog> allCatalog(@RequestParam String local){
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
	public PortalApplication viewApp(@PathVariable int appId, @RequestParam String local){
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
	public JsonPage<PortalAppVersionFile> queryAppVersions(@PathVariable int appId,  @RequestParam String local, DataGridModel dgm) {
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
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-1</td><td>错误</td></tr>
	 * 	<tr><td>0</td><td>没有登录</td></tr>
	 * 	<tr><td>1</td><td>成功</td></tr>
	 * <tr><td>其它</td><td>后台异常</td></tr>
	 * </table>
	 * @param appComment
	 * @param result
	 * @param req
	 * @return
	 */
	@RequestMapping(value="portal/app/comment/add", method=RequestMethod.POST)
	@ResponseBody
	public int addAppComment(@ModelAttribute AppComment appComment
			, BindingResult result, HttpServletRequest req){
		if (result.hasErrors()) {
			return -1;
		}
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			log.error("not login comment ...");
			return 0;
		}
		appComment.setUserId(account.getId());
		appCommentService.saveOrUpdate(appComment);
		return 1;
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
//			return -2;
//			throw new Exception("session time out...");
		}
		Application app = applicationService.queryById(appId);
		String fileName=app.getAppFilePath();
		if (!StringUtils.hasText(fileName)) {
			// 文件不存在
//			throw new Exception();
		}
//		String uri=req.getProtocol()+req.getRemoteHost()+req.getRemotePort()+"/"+fileName;
//		AppDownloadLog downlog=new AppDownloadLog();
//		downlog.setAccountId(account.getId());
//		downlog.setAppId(appId);
//		appDownloadLogService.saveOrUpdate(downlog);
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
	public List<PortalApplication> queryHotFree(@PathVariable int limit, @RequestParam String local){
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
	public List<PortalApplication> queryHotNewFree(@PathVariable int limit, @RequestParam String local){
		return applicationService.queryHotNewFree(local, limit);
	}
	
	/**
	 * 检测应用是否已存在
	 * <br>portal/app/check?appName=&local=cn|en
	 * @param appName
	 * @param local
	 * @return
	 */
	@RequestMapping(value="app/check", method=RequestMethod.GET)
	public @ResponseBody int checkAppliction(@RequestParam String appName, @RequestParam String local){
		return applicationService.checkApplication(appName, local);
	}
	/**
	 * 检测应用分类是否存在
	 * <br>portal/app/catalog/check?appName=&local=cn|en
	 * @param name
	 * @param local
	 * @return
	 */
	@RequestMapping(value="app/catalog/check", method=RequestMethod.GET)
	public @ResponseBody int checkCatalog(@RequestParam String name, @RequestParam String local){
		return appCatalogService.checkCatalog(name, local);
	}
}
