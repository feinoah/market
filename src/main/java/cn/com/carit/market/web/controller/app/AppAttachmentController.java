package cn.com.carit.market.web.controller.app;
import cn.com.carit.market.service.app.AppAttachmentService;
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

import cn.com.carit.market.bean.app.AppAttachment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AppAttachmentController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/attachment")
public class AppAttachmentController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppAttachmentService appAttachmentService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/attachment/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppAttachment());
		return "admin/app/attachment";
	}
	
	/**
	 * 增加/更新
	 * admin/app/attachment/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AppAttachment appAttachment, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		appAttachmentService.saveOrUpdate(appAttachment);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/attachment/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AppAttachment view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appAttachmentService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/attachment/delete/{id}
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
		return appAttachmentService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/attachment/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(@ModelAttribute AppAttachment appAttachment, BindingResult result,DataGridModel dgm){
		return appAttachmentService.queryByExemple(appAttachment, dgm);
	}
}
