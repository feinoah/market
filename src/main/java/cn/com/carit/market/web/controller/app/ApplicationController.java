package cn.com.carit.market.web.controller.app;
import java.io.File;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import cn.com.carit.market.bean.app.AppCatalog;
import cn.com.carit.market.bean.app.Application;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppCatalogService;
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
	@Resource
	private AppCatalogService appCatalogService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/application/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new Application());
		AppCatalog catalog=new AppCatalog();
		catalog.setStatus((byte)Constants.STATUS_VALID);
		model.addAttribute("catalogList", appCatalogService.queryByExemple(catalog));
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
	public int save(@ModelAttribute Application application, BindingResult result
			, HttpServletRequest request) throws Exception{
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request; 
		String filePath = request.getSession().getServletContext().getRealPath(Constants.BASE_PATH_ICON );
		//页面控件的文件流
        MultipartFile multipartFile = multipartRequest.getFile("file");
        // 获取文件的后缀 
		String suffix = multipartFile.getOriginalFilename().substring(
				multipartFile.getOriginalFilename().lastIndexOf("."));
		// 随机文件名
		String fileName =  System.nanoTime() + suffix;// 构建文件名称
		File file = new File(filePath+File.separator+fileName);
		multipartFile.transferTo(file);
		application.setIcon(Constants.BASE_PATH_ICON+File.separator+fileName);
		applicationService.saveOrUpdate(application);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/application/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public Application view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return applicationService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/application/delete/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@PathVariable int id){
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
	public JsonPage query(@ModelAttribute Application application, BindingResult result,DataGridModel dgm){
		return applicationService.queryByExemple(application, dgm);
	}
}
