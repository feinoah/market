package cn.com.carit.market.web.controller.app;
import java.util.List;

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
			log.error(result.getAllErrors().toString());
			return -1;
		}
		if (accountInfo.getId()<=0) { // 新增时
			if (!StringUtils.hasText(accountInfo.getEmail())) {
				log.error("email can't be empty");
				return 0;
			}
			if (!StringUtils.hasText(accountInfo.getPassword())) {
				log.error("password can't be empty");
				return 0;
			}
		}
		accountInfoService.saveOrUpdate(accountInfo);
		return 1;
	}
	
	/**
	 * 查看
	 * admin/app/account/view?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="view", method=RequestMethod.GET)
	@ResponseBody
	public AccountInfo view(@RequestParam int id){
		if (id<=0) {
			log.debug("The param id must be bigger than 0...");
			return null;
		}
		return accountInfoService.queryById(id);
	}
	
	/**
	 * 删除
	 * admin/app/account/delete?id=|ids=
	 * @param id
	 * @return
	 */
	@RequestMapping(value="delete", method=RequestMethod.GET)
	@ResponseBody
	public int delete(@RequestParam int id, @RequestParam(required=false) String ids){
		if (StringUtils.hasText(ids)) {
			return accountInfoService.batchDelete(ids);
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
	public JsonPage<AccountInfo> query(@ModelAttribute AccountInfo accountInfo, BindingResult result,DataGridModel dgm){
		return accountInfoService.queryByExemple(accountInfo, dgm);
	}
	
	/**
	 * 封号
	 * admin/app/account/lock?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="lock", method=RequestMethod.GET)
	@ResponseBody
	public int lock(@RequestParam(required=false) int id, @RequestParam(required=false)String ids){
		if (StringUtils.hasText(ids)) {
			return accountInfoService.batchLockAccount(ids);
		}
		return accountInfoService.lockAccount(id);
	}
	/**
	 * 解封
	 * admin/app/account/unlock?id={id}
	 * @param id
	 * @return
	 */
	@RequestMapping(value="unlock", method=RequestMethod.GET)
	@ResponseBody
	public int unlock(@RequestParam(required=false) int id, @RequestParam(required=false)String ids){
		if (StringUtils.hasText(ids)) {
			return accountInfoService.batchUnLockAccount(ids);
		}
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
