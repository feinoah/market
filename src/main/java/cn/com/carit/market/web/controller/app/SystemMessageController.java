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

import cn.com.carit.market.bean.app.SystemMessage;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AccountInfoService;
import cn.com.carit.market.service.app.SystemMessageService;

@Controller
@RequestMapping(value="admin/app/sysmessage")
public class SystemMessageController {
	private final Logger logger = Logger.getLogger(getClass());
	@Resource
	private SystemMessageService systemMessageService;
	@Resource
	private AccountInfoService accountInfoService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/sysmessage
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new SystemMessage());
		model.addAttribute("accountList", accountInfoService.query());
		return "admin/app/sysmessage";
	}
	
	/**
	 * 增加/更新
	 * admin/app/sysmessage/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute SystemMessage systemMessage, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			logger.error(result.getAllErrors().toString());
			return -1;
		}
		return systemMessageService.batchSave(systemMessage);
	}
	
	/**
	 * 查看
	 * admin/app/sysmessage/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public SystemMessage view(@RequestParam int id){
		if (id<=0) {
			logger.debug("The param id must be bigger than 0...");
			return null;
		}
		return systemMessageService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/sysmessage/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam(required=false) int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return systemMessageService.batchDelete(ids);
		}
		return systemMessageService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/sysmessage/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage<SystemMessage> query(@ModelAttribute SystemMessage systemMessage, BindingResult result,DataGridModel dgm){
		return systemMessageService.queryByExemple(systemMessage, dgm);
	}
}
