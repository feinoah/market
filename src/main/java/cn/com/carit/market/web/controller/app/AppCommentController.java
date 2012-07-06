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

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppCommentService;

/**
 * AppCommentController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/comment")
public class AppCommentController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppCommentService appCommentService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/comment/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppComment());
		return "admin/app/comment";
	}
	
	/**
	 * 增加/更新
	 * admin/app/comment/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AppComment appComment, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		appCommentService.saveOrUpdate(appComment);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/comment/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public AppComment view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return appCommentService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/comment/delete?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return appCommentService.batchDelete(ids);
		}
		return appCommentService.delete(id);
	}
	
	/**
	 * 批量删除
	 * admin/app/comment/delete/batch
	 * @param request
	 * @return
	 */
	@RequestMapping(value="delete/batch", method=RequestMethod.POST)
	@ResponseBody
	public int [] deleteRows(@RequestParam String [] ids){
		return appCommentService.batchDelete(ids);
	}
	
	/**
	 * 查询
	 * admin/app/comment/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<AppComment> query(@ModelAttribute AppComment appComment, BindingResult result,DataGridModel dgm){
		return appCommentService.queryByExemple(appComment, dgm);
	}
}
