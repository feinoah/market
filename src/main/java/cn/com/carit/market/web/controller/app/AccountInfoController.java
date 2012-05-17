package cn.com.carit.market.web.controller.app;
import cn.com.carit.market.service.app.AccountInfoService;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;

/**
 * AccountInfoController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="app/accountInfo")
public class AccountInfoController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AccountInfoService accountInfoService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * app/accountInfo/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AccountInfo());
		return "/app/accountInfo";
	}
	
	/**
	 * 增加/更新
	 * app/accountInfo/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(AccountInfo accountInfo, BindingResult result){
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		return accountInfoService.saveOrUpdate(accountInfo);
	}
	
	/**
	 * 查看
	 * app/accountInfo/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="{id}", method=RequestMethod.GET)
	@ResponseBody
	public AccountInfo view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must bigger than 0...");
			return null;
		}
		return accountInfoService.queryById(id);
	}
	
	/**
	 * 删除
	 * app/accountInfo/delete/{id}
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
		return accountInfoService.delete(id);
	}
	
	/**
	 * 查询
	 * app/accountInfo/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(AccountInfo accountInfo,DataGridModel dgm){
		return accountInfoService.queryByExemple(accountInfo, dgm);
	}
}
