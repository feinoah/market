package cn.com.carit.market.web.controller.app;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@RequestMapping(value="app/appVersionFile")
public class AppVersionFileController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppVersionFileService appVersionFileService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * app/appVersionFile/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppVersionFile());
		return "/app/appVersionFile";
	}
	
	/**
	 * 增加/更新
	 * app/appVersionFile/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(AppVersionFile appVersionFile, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return appVersionFileService.saveOrUpdate(appVersionFile);
	}
	
	/**
	 * 查看
	 * app/appVersionFile/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AppVersionFile view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return null;
		}
		return appVersionFileService.queryById(id);
	}
	
	/**
	 * 删除
	 * app/appVersionFile/delete/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return -1;
		}
		return appVersionFileService.delete(id);
	}
	
	/**
	 * 查询
	 * app/appVersionFile/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(AppVersionFile appVersionFile,DataGridModel dgm){
		return appVersionFileService.queryByExemple(appVersionFile, dgm);
	}
}
