package cn.com.carit.market.web.controller.app;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.ApplicationService;

/**
 * ApplicationController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/application")
public class ApplicationController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private ApplicationService applicationService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/application/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new Application());
		return "admin/app/application";
	}
	
	/**
	 * 增加/更新
	 * admin/app/application/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute Application application, BindingResult result, HttpServletRequest request) {
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		
		if (!StringUtils.hasText(application.getAppName())) {
			log.debug("appName must be not empty ...");
			return -1;
		}
		if (!StringUtils.hasText(application.getEnName())) {
			log.debug("enName must be not empty ...");
			return -1;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		MultipartFile apkMultipartFile = multipartRequest.getFile("apkFile");
		if(application.getId()==0&&(apkMultipartFile==null||apkMultipartFile.getOriginalFilename().length()<=0)){
			// 新增时必须上传Apk文件
			log.debug("apkFile must be not empty ...");
			return -1;
		}
		try {
        	StringBuilder images=new StringBuilder();
        	long radom=System.nanoTime();
        	String suffix = "";
        	String fileName = "";
        	if (apkMultipartFile!=null && apkMultipartFile.getOriginalFilename().length()>0) { // 有APK文件
        		suffix = apkMultipartFile.getOriginalFilename().substring(
        				apkMultipartFile.getOriginalFilename().lastIndexOf("."));
        		// 随机文件名
        		fileName =  application.getEnName()+"_"+radom+ suffix.toLowerCase();// 构建文件名称
        		File apkFile=AttachmentUtil.getApkFile(fileName);
        		apkMultipartFile.transferTo(apkFile);
        		application.setAppFilePath(Constants.BASE_PATH_APK+fileName);
			}
        	//页面控件的文件流
        	MultipartFile multipartFile = multipartRequest.getFile("iconFile");
	        if (multipartFile!=null&&multipartFile.getOriginalFilename().length()>0) { // 有ICON文件
	        	// 获取文件的后缀 
	        	suffix = multipartFile.getOriginalFilename().substring(
	        			multipartFile.getOriginalFilename().lastIndexOf("."));
	        	// 随机文件名
	        	fileName =  application.getEnName()+"_"+radom+ suffix.toLowerCase();// 构建文件名称
	        	File file = AttachmentUtil.getIconFile(fileName);
        		multipartFile.transferTo(file);
	        	application.setIcon(Constants.BASE_PATH_ICON+fileName);
			}
	        MultipartFile bigIconMultipartFile = multipartRequest.getFile("bigIconFile");
	        if (bigIconMultipartFile!=null&&bigIconMultipartFile.getOriginalFilename().length()>0) { // 有ICON文件
	        	// 获取文件的后缀 
	        	suffix = bigIconMultipartFile.getOriginalFilename().substring(
	        			bigIconMultipartFile.getOriginalFilename().lastIndexOf("."));
	        	// 随机文件名
	        	fileName =  application.getEnName()+"_"+radom+"_big"+ suffix.toLowerCase();// 构建文件名称
	        	File file = AttachmentUtil.getIconFile(fileName);
	        	bigIconMultipartFile.transferTo(file);
	        	application.setBigIcon(Constants.BASE_PATH_ICON+fileName);
			}
			//页面控件的文件流
	        List<MultipartFile> imageFiles = multipartRequest.getFiles("imageFile");
	        int i=0;
	        for (MultipartFile imageFile : imageFiles) {
	        	if (imageFile!=null&&imageFile.getOriginalFilename().length()>0) {
	        		fileName = imageFile.getOriginalFilename();  
	        		String extName = fileName.substring(fileName.lastIndexOf("."));  
	        		String lastFileName = application.getEnName()+"_"+radom+"_"+(i+1)+extName.toLowerCase();
	        		FileCopyUtils.copy(imageFile.getBytes(),AttachmentUtil.getImageFile(lastFileName)); 
	        		if (i<4) {
	        			images.append(Constants.BASE_PATH_IMAGE+lastFileName).append(";");
	        		} else {
	        			images.append(Constants.BASE_PATH_IMAGE+lastFileName);
	        		}
				}
        		i++;
			}
	        application.setImages(images.toString());
	        applicationService.saveOrUpdate(application);
	        return 1;
        } catch (IllegalStateException e) {
        	log.error("upload file error..."+e.getMessage());
        	return -1;
        } catch (IOException e) {
        	log.error("upload file error..."+e.getMessage());
        	return -1;
        }
	}
	
	/**
	 * 查看
	 * admin/app/application/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public Application view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return applicationService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/application/delete?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return -1;
		}
		return applicationService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/application/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<Application> query(@ModelAttribute Application application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
	
	/**
	 * 所有应用
	 * admin/app/application/all
	 * @return
	 */
	@RequestMapping(value="/all", method=RequestMethod.GET)
	@ResponseBody
	public List<Application> allApps(){
		Application application=new Application();
		application.setStatus(Constants.STATUS_VALID);
		return applicationService.queryByExemple(application);
	}
}
