package cn.com.carit.market.web.controller.app;
import cn.com.carit.market.service.app.AppDownloadLogService;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AppDownloadLog;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AppDownloadLogController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="app/appDownloadLog")
public class AppDownloadLogController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppDownloadLogService appDownloadLogService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * app/appDownloadLog/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppDownloadLog());
		return "/app/appDownloadLog";
	}
	
	/**
	 * 增加/更新
	 * app/appDownloadLog/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(AppDownloadLog appDownloadLog, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return appDownloadLogService.saveOrUpdate(appDownloadLog);
	}
	
	/**
	 * 查看
	 * app/appDownloadLog/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AppDownloadLog view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return null;
		}
		return appDownloadLogService.queryById(id);
	}
	
	/**
	 * 删除
	 * app/appDownloadLog/delete/{id}
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
		return appDownloadLogService.delete(id);
	}
	
	/**
	 * 查询
	 * app/appDownloadLog/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(AppDownloadLog appDownloadLog,DataGridModel dgm){
		return appDownloadLogService.queryByExemple(appDownloadLog, dgm);
	}
}
