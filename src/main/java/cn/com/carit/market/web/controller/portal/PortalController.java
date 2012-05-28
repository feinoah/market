package cn.com.carit.market.web.controller.portal;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
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
import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.bean.portal.PortalAppCatalog;
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
public class PortalController {
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
	 * 注册帐号
	 * portal/register
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-1</td><td>错误</td><</tr>
	 * 	<tr><td>0</td><td>参数异常</td><</tr>
	 * 	<tr><td>1</td><td>成功</td><</tr>
	 * 	<tr><td>其它</td><td>后台异常</td><</tr>
	 * </table>
	 * @param accountInfo
	 * @param result
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="register", method=RequestMethod.POST)
	@ResponseBody
	public int register(@ModelAttribute AccountInfo accountInfo,BindingResult result) throws Exception{
		if (result.hasErrors()) {
			return -1;
		}
		if (!StringUtils.hasText(accountInfo.getEmail())) {
			return 0;
		}
		if (!StringUtils.hasText(accountInfo.getPassword())) {
			return 0;
		}
		accountInfoService.saveOrUpdate(accountInfo);
		return 1;
	}
	
	/**
	 * 登录 portal/login
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-3</td><td>密码错误次数太多，临时限制登录</td><</tr>
	 * 	<tr><td>-2</td><td>账号被锁定</td><</tr>
	 * 	<tr><td>-1</td><td>账号不存在</td><</tr>
	 * 	<tr><td>0</td><td>密码错误</td><</tr>
	 * 	<tr><td>1</td><td>登录成功</td><</tr>
	 * 	<tr><td>其它</td><td>后台异常</td><</tr>
	 * </table>
	 * @param email
	 * @param password
	 * @param req
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="login", method=RequestMethod.POST)
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
		Map<String, Object> resultMap=accountInfoService.login(email, password
				, req.getRemoteAddr());
		Integer answerCode=(Integer) resultMap.get(Constants.ANSWER_CODE);
		if (answerCode!=null ) {//有响应
			if (answerCode.intValue()==1) {// 登录成功
				// 清除密码错误次数
				AccountInfo account=(AccountInfo) resultMap.get(email);
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, 0);
				session.setAttribute(Constants.PORTAL_USER, account);
			}
			if (answerCode.intValue()==0) {// 密码错误
				session.setAttribute(Constants.PASSWORD_ERROR_COUNT+email, errorCount+1);
			}
		}
		return answerCode;
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
	 * 修改密码；请求参数 oldPassword、newPassword
	 * portal/account/changepwd
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-2</td><td>session超时</td><</tr>
	 * 	<tr><td>-1</td><td>参数错误（密码、新密码不能为空）</td><</tr>
	 * 	<tr><td>0</td><td>密码错误</td><</tr>
	 * 	<tr><td>1</td><td>成功</td><</tr>
	 * 	<tr><td>其它</td><td>后台异常</td><</tr>
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
	 * 修改头像；请求参数 file
	 * portal/account/changephoto
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-2</td><td>session超时</td><</tr>
	 * 	<tr><td>-1</td><td>文件上传失败</td><</tr>
	 * 	<tr><td>1</td><td>成功</td><</tr>
	 * 	<tr><td>其它</td><td>后台异常</td><</tr>
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
	 * 修改资料
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
	 * 所有用户
	 * portal/account/all
	 * @return
	 */
	@RequestMapping(value="account/all", method=RequestMethod.GET)
	@ResponseBody
	public List<AccountInfo> allAccount(){
		AccountInfo account=new AccountInfo();
		account.setStatus((byte)Constants.STATUS_VALID);
		return accountInfoService.queryByExemple(account);
	}
	
	/**
	 * 所有应用分类接口
	 * portal/catalog/all/{local}
	 * @return json
	 */
	@RequestMapping(value="catalog/all/{local}", method=RequestMethod.GET)
	@ResponseBody
	public List<PortalAppCatalog> allCatalog(@PathVariable String local){
		return appCatalogService.queryAll(local);
	}
	
	/**
	 * 按条件查询应用
	 * portal/app/query
	 * <br>例如：portal/app/query?page=1&rows=10&appName=?...
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
	public JsonPage queryApps(@ModelAttribute PortalApplication application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	/**
	 * 所有应用
	 * portal/app/all
	 * @return
	 */
	@RequestMapping(value="app/all", method=RequestMethod.GET)
	@ResponseBody
	public List<Application> allApps(){
		Application application=new Application();
		application.setStatus(Constants.STATUS_VALID);
		return applicationService.queryByExemple(application);
	}
	
	/**
	 * 查看App
	 * portal/app/{appId}/{local}
	 * @param appId
	 * @return json {@link PortalApplication}
	 */
	@RequestMapping(value="app/{appId}/{local}", method=RequestMethod.GET)
	@ResponseBody
	public PortalApplication viewApp(@PathVariable int appId, @PathVariable String local){
		return applicationService.queryAppById(appId, local);
	}
	
	/**
	 * 获取应用的历史版本
	 * portal/app/versions/{appId}
	 * @param appId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/versions/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage queryAppVersions(@PathVariable int appId, DataGridModel dgm) {
		PortalAppVersionFile appVersionFile=new PortalAppVersionFile();
		appVersionFile.setAppId(appId);
		return appVersionFileService.queryByExemple(appVersionFile, dgm);
	}
	
	/**
	 * 获取应用评论
	 * portal/app/comments/{appId}
	 * @param appId
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="app/comments/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage queryAppComments(@PathVariable int appId, DataGridModel dgm) {
		return appCommentService.queryComment(appId, dgm);
	}
	
	/**
	 * 增加评论
	 * portal/app/comment/add
	 * <table>
	 * 	<tr><th>返回值</th><th>描述</th></tr>
	 * 	<tr><td>-1</td><td>错误</td><</tr>
	 * 	<tr><td>0</td><td>没有登录</td><</tr>
	 * 	<tr><td>1</td><td>成功</td><</tr>
	 * <tr><td>其它</td><td>后台异常</td><</tr>
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
	 * 下载应用
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
//		AppDownloadLog downlog=new AppDownloadLog();
//		downlog.setAccountId(account.getId());
//		downlog.setAppId(appId);
//		appDownloadLogService.saveOrUpdate(downlog);
		return "forward:/"+fileName;
	}
	
}
