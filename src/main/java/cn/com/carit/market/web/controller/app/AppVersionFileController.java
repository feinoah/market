package cn.com.carit.market.web.controller.app;
import java.io.File;
import java.io.IOException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.common.utils.MD5Util;
import cn.com.carit.market.service.app.AppVersionFileService;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * AppVersionFileController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/version")
public class AppVersionFileController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppVersionFileService appVersionFileService;
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/version/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppVersionFile());
		return "admin/app/version";
	}
	
	/**
	 * 增加/更新
	 * admin/app/version/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AppVersionFile appVersionFile, BindingResult result
			, HttpServletRequest request){
		if (result.hasErrors()) {
			log.error(result.getAllErrors().toString());
			return -1;
		}
		if (appVersionFile.getAppId()==null||appVersionFile.getAppId().intValue()<=0) {
			log.error("appId must be bigger than 0 ...");
			return -1;
		}
		if (!StringUtils.hasText(appVersionFile.getVersion())) {
			log.error("version must be not empty ...");
			return -1;
		}
		Application application=applicationService.queryById(appVersionFile.getAppId());
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		//页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("file");
        try {
	        if (multipartFile!=null&&multipartFile.getOriginalFilename().length()>0) {
	        	// 获取文件的后缀 
	        	String suffix = multipartFile.getOriginalFilename().substring(
	        			multipartFile.getOriginalFilename().lastIndexOf("."));
	        	String prefix=MD5Util.md5Hex(application.getEnName()+application.getAppName()+application.getVersion()+System.nanoTime());
	        	String fileName = (prefix + suffix).toLowerCase();// 构建文件名称
	        	File file = AttachmentUtil.getApkFile(fileName);
				multipartFile.transferTo(file);
	        	appVersionFile.setFilePath(Constants.BASE_PATH_APK+fileName);
			}
        } catch (IllegalStateException e) {
        	log.error("upload file error..."+e.getMessage());
        	log.error(e.getStackTrace());
        	return -1;
        } catch (IOException e) {
        	log.error("upload file error..."+e.getMessage());
        	log.error(e.getStackTrace());
        	return -1;
        } catch (Exception e) {
        	log.error("upload file error..."+e.getMessage());
        	log.error(e.getStackTrace());
        	return -1;
		}
		appVersionFileService.saveOrUpdate(appVersionFile);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/version/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public AppVersionFile view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appVersionFileService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/version/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return appVersionFileService.batchDelete(ids);
		}
		return appVersionFileService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/version/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<AppVersionFile> query(@ModelAttribute AppVersionFile appVersionFile, BindingResult result,DataGridModel dgm){
		return appVersionFileService.queryByExemple(appVersionFile, dgm);
	}
}
