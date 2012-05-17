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

import cn.com.carit.market.bean.app.AppComment;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AppCommentService;

/**
 * AppCommentController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="app/appComment")
public class AppCommentController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AppCommentService appCommentService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * app/appComment/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AppComment());
		return "/app/appComment";
	}
	
	/**
	 * 增加/更新
	 * app/appComment/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(AppComment appComment, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return appCommentService.saveOrUpdate(appComment);
	}
	
	/**
	 * 查看
	 * app/appComment/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AppComment view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return null;
		}
		return appCommentService.queryById(id);
	}
	
	/**
	 * 删除
	 * app/appComment/delete/{id}
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
		return appCommentService.delete(id);
	}
	
	/**
	 * 查询
	 * app/appComment/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(AppComment appComment,DataGridModel dgm){
		return appCommentService.queryByExemple(appComment, dgm);
	}
}
