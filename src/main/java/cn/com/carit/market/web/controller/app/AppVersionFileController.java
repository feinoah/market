package cn.com.carit.market.web.controller.app;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AppVersionFile;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppVersionFileService;

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
	public int save(@ModelAttribute AppVersionFile appVersionFile, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		appVersionFileService.saveOrUpdate(appVersionFile);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/version/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AppVersionFile view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appVersionFileService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/version/delete/{id}
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
		return appVersionFileService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/version/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(@ModelAttribute AppVersionFile appVersionFile, BindingResult result,DataGridModel dgm){
		return appVersionFileService.queryByExemple(appVersionFile, dgm);
	}
}
