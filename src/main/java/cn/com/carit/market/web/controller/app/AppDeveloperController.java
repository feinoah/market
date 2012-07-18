package cn.com.carit.market.web.controller.app;

import javax.annotation.Resource;

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

import cn.com.carit.market.bean.app.AppDeveloper;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppDeveloperService;

@Controller
@RequestMapping(value="admin/app/developer")
public class AppDeveloperController {
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private AppDeveloperService appDeveloperService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/developer
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppDeveloper());
		return "admin/app/developer";
	}
	
	/**
	 * 增加/更新
	 * admin/app/developer/save
	 * @param appDeveloper
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AppDeveloper developer, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			log.error(result.getAllErrors().toString());
			return -1;
		}
		return appDeveloperService.saveOrUpdate(developer);
	}
	
	/**
	 * 查看
	 * admin/app/developer/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public AppDeveloper view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appDeveloperService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/developer/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return appDeveloperService.batchDelete(ids);
		}
		return appDeveloperService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/developer/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<AppDeveloper> query(@ModelAttribute AppDeveloper developer, BindingResult result,DataGridModel dgm){
		return appDeveloperService.queryByExemple(developer, dgm);
	}
}
