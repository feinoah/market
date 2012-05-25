package cn.com.carit.market.web.controller.portal;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.stream.FileImageInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
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
	private AppCatalogService appCatalogService;
	
	@Resource
	private ApplicationService applicationService;
	
	@Resource
	private AppVersionFileService appVersionFileService;
	
	@Resource
	private AppCommentService appCommentService;
	
	@Resource
	private AccountInfoService accountInfoService;
	
	@Resource
	private AppDownloadLogService appDownloadLogService;
	
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
	 * portal/catalog/all
	 * @return json
	 */
	@RequestMapping(value="catalog/all", method=RequestMethod.GET)
	@ResponseBody
	public List<AppCatalog> allCatalog(){
		AppCatalog catalog=new AppCatalog();
		catalog.setStatus((byte)Constants.STATUS_VALID);
		return appCatalogService.queryByExemple(catalog);
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
	 * 按条件查询应用
	 * portal/app/query
	 * <br>例如：portal/app/query?page=1&rows=10&appName=?...
	 * <table>
	 * 	<tr><th>参数名称</th><th>描述</th><th>类型</th><th>是否必须</th></tr>
	 * 	<tr><td>page</td><td>页码</td><td>int</td><td>F</td></tr>
	 * 	<tr><td>rows</td><td>每页显示数</td><td>int</td><td>F</td></tr>
	 * 	<tr><td>Application</td><td>Application相关属性参数</td><td>{@link Application}</td><td>F</td></tr>
	 * </table>
	 * @return json
	 */
	@RequestMapping(value="app/query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage queryApps(@ModelAttribute Application application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	/**
	 * 查看App
	 * portal/app/{appId}
	 * @param appId
	 * @return json {@link Application}
	 */
	@RequestMapping(value="app/{appId}", method=RequestMethod.GET)
	@ResponseBody
	public Application viewApp(@PathVariable int appId){
		return applicationService.queryById(appId);
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
		AppVersionFile appVersionFile=new AppVersionFile();
		appVersionFile.setAppId(appId);
		appVersionFile.setStatus(Constants.STATUS_VALID);
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
		AppComment appComment=new AppComment();
		appComment.setAppId(appId);
		appComment.setStatus(Constants.STATUS_VALID);
		return appCommentService.queryByExemple(appComment, dgm);
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
	@RequestMapping(value="portal/app/comment/add", method=RequestMethod.GET)
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
	 * @param res
	 * @param req
	 * @throws Exception 
	 */
	@RequestMapping(value="app/down/{appId}", method=RequestMethod.GET)
	public void appDown(@PathVariable int appId,HttpServletResponse res,HttpServletRequest req) throws Exception{
		AccountInfo account=(AccountInfo) req.getSession().getAttribute(Constants.PORTAL_USER);
		if (account==null) {
			//session超时
			log.warn("session time out...");
//			return -2;
			throw new Exception("session time out...");
		}
		BufferedInputStream in=null;
		BufferedOutputStream out=null;
		Application app = applicationService.queryById(appId);
		String fileName=app.getAppFilePath();
		int index=fileName.indexOf(File.separator);
		if (index!=-1) {
			fileName=fileName.substring(index);
		}
		File file = AttachmentUtil.getApkFile(fileName);
		res.setContentType("application/x-msdownload");//oper save as 对话框
		try {
			res.setHeader("Content-Disposition",
					"attachment;filename="+URLEncoder.encode(app.getAppName()+app.getVersion(), "UTF-8")+".apk");
			in=new BufferedInputStream(new FileInputStream(file));
			out=new BufferedOutputStream(new BufferedOutputStream(res.getOutputStream()));
			byte[] buffer=new byte[1024*8];
			int j=-1;
			while((j=in.read(buffer))!=-1) {
				out.write(buffer,0,j);
			}
			AppDownloadLog downlog=new AppDownloadLog();
			downlog.setAccountId(account.getId());
			downlog.setAppId(appId);
			appDownloadLogService.saveOrUpdate(downlog);
		} catch (UnsupportedEncodingException e) {
			log.error("download error..."+e.getMessage());
		} catch (IOException e) {
			log.error(e.getMessage());
		} finally {
			try {
				if (in != null) {
					in.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				log.error(e.getMessage());
			}
		}
	}
	
	/**
	 * 取图片
	 * partal/image/{fileName}/{type}
	 * @param path
	 * @param type
	 * @throws IOException 
	 * @throws FileNotFoundException 
	 */
	@RequestMapping(value="image/{fileName}/{type}", method=RequestMethod.GET)
	public void getImage(@PathVariable String fileName, @PathVariable int type
			, HttpServletResponse response) throws FileNotFoundException, IOException{
		File image=null;
		int index=fileName.indexOf(File.separator);
		if (index!=-1) {
			fileName=fileName.substring(index);
		}
		if (type==Constants.IMAGE_TYPE_ICON) { // 图标
			image=AttachmentUtil.getIconFile(fileName);
		} else if(type==Constants.IMAGE_TYPE_PHOTO) { // 用户头像
			image=AttachmentUtil.getPhotoFile(fileName);
		} else {
			image=AttachmentUtil.getImageFile(fileName);
		}
		response.setContentType("image/jpeg; charset=UTF-8");
		FileImageInputStream is=new FileImageInputStream(image);
		OutputStream out=response.getOutputStream();
		byte[] buffer = new byte[1024];
		int i=-1;
		while ((i = is.read(buffer)) != -1) {
			out.write(buffer, 0, i);
		   }
		out.flush();
		out.close();
		is.close();
	}
}
