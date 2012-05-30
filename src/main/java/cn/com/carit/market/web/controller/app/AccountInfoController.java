package cn.com.carit.market.web.controller.app;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.app.AccountInfoService;

/**
 * AccountInfoController
 * Auto generated Code
 */
@Controller
@RequestMapping(value="admin/app/account")
public class AccountInfoController {
	private final Logger log = Logger.getLogger(getClass());
	
	@Resource
	private AccountInfoService accountInfoService;
	
	/**
	 * 啥都不干，单纯跳转到页面
	 * admin/app/account/
	 * @return
	 */
	@RequestMapping(method=RequestMethod.GET)
	public String index(Model model){
		model.addAttribute(new AccountInfo());
		return "admin/app/account";
	}
	
	/**
	 * 增加/更新
	 * admin/app/account/save
	 * @param user
	 * @param result
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="save", method=RequestMethod.POST)
	@ResponseBody
	public int save(@ModelAttribute AccountInfo accountInfo, BindingResult result) throws Exception{
		if (result.hasErrors()) {
			log.debug(result.getAllErrors().toString());
			return -1;
		}
		if (!StringUtils.hasText(accountInfo.getEmail())) {
			return 0;
		}
		if (!StringUtils.hasText(accountInfo.getPassword())) {
			return 0;
		}
		accountInfoService.saveOrUpdate(accountInfo);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/account/view/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view/{id}", method=RequestMethod.GET)
	@ResponseBody
	public AccountInfo view(@PathVariable int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return accountInfoService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/account/delete/{id}
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
		return accountInfoService.delete(id);
	}
	
	/**
	 * 查询
	 * admin/app/account/query
	 * @return json
	 */
	@RequestMapping(value="query", method=RequestMethod.GET)
	@ResponseBody
	public JsonPage query(@ModelAttribute AccountInfo accountInfo, BindingResult result,DataGridModel dgm){
		return accountInfoService.queryByExemple(accountInfo, dgm);
	}
	
	/**
	 * 封号
	 * admin/app/account/lock/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="lock/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int lock(@PathVariable int id){
		return accountInfoService.lockAccount(id);
	}
	/**
	 * 解封
	 * admin/app/account/unlock/{id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="unlock/{id}", method=RequestMethod.GET)
	@ResponseBody
	public int unlock(@PathVariable int id){
		return accountInfoService.unLockAccount(id);
	}
	/**
	 * 所有帐号
	 * @return
	 */
	@RequestMapping(value="all")
	@ResponseBody
	public List<AccountInfo> queryAll(){
		return accountInfoService.query();
	}
}
