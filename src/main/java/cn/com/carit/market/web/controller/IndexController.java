package cn.com.carit.market.web.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.Constants;
import cn.com.carit.platform.client.CaritClient;
import cn.com.carit.platform.client.ClientUtils;

@Controller
public class IndexController extends BaseController {
	
	@Resource
	private CaritClient caritClient;
	
	/**
	 * 查询位置信息
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping(value="query/locations/{deviceId}/{accountId}", method=RequestMethod.GET)
	@ResponseBody
	public String queryLocations(@PathVariable String deviceId
			, @PathVariable String accountId
			, @RequestParam(required=false) String type
			, @RequestParam(required=false) Date startTime
			, @RequestParam(required=false) Date endTime){
		Map<String, String> paramValues=caritClient.buildParamValues("platform.location.search", "1.0");
		
		paramValues.put("deviceId", deviceId);
		paramValues.put("accountId", accountId);
		if (StringUtils.hasText(type)) {
			paramValues.put("type", type);
		}
		if (startTime!=null) {
			paramValues.put("startTime", String.valueOf(startTime.getTime()));
		}
		if (endTime!=null) {
			paramValues.put("endTime", String.valueOf(endTime.getTime()));
		}
		// 不需要签名的参数放后面
				paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		return caritClient.getHttpResponse(paramValues);
	}
	
	/**
	 * @param hl
	 * @return
	 */
	@RequestMapping(value="profile", method=RequestMethod.GET)
	public String profile(){
		setObdCurrentData();
		return "profile";
	}
	
	@RequestMapping(value="obd", method=RequestMethod.GET)
	public String obdIndex(){
		setObdCurrentData();
		return "obd-info";
	}
	@RequestMapping(value="obd/{deviceId}/{accountId}", method=RequestMethod.GET)
	public @ResponseBody String obdInfo(@PathVariable String deviceId, @PathVariable String accountId) throws Exception {
		Map<String, String> paramValues=caritClient.buildParamValues("platform.obd.newestData", "1.0");
		paramValues.put("deviceId", deviceId);
		paramValues.put("accountId", accountId);
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		return caritClient.getHttpResponse(paramValues);
	}
	
	/**
	 * 查询历史数据
	 * @param deviceId
	 * @param page
	 * @param rows
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	@RequestMapping(value="obd/search/{deviceId}/{accountId}", method=RequestMethod.GET)
	public @ResponseBody String searchObdData(@PathVariable String deviceId
			, @PathVariable String accountId
			, @RequestParam(required=false) int page
			, @RequestParam(required=false) int rows
			, @RequestParam(required=false) Date startTime
			, @RequestParam(required=false) Date endTime){
		Map<String, String> paramValues=caritClient.buildParamValues("platform.obd.search", "1.0");
		paramValues.put("deviceId", deviceId);
		paramValues.put("accountId", accountId);
		if (page==0) {
			page=1;
		}
		paramValues.put("page", String.valueOf(page));
		if (rows==0) {
			rows=5;
		}
		paramValues.put("rows", String.valueOf(rows));
		if (startTime!=null) {
			paramValues.put("startTime", String.valueOf(startTime.getTime()));
		}
		if (endTime!=null) {
			paramValues.put("endTime", String.valueOf(endTime.getTime()));
		}
		// 不需要签名的参数放后面
				paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		return caritClient.getHttpResponse(paramValues);
	}
	
	@RequestMapping(value="poi", method=RequestMethod.GET)
	public String poiInfo(){
		return "poi";
	}
	
	@RequestMapping(value="location", method=RequestMethod.GET)
	public String location(){
		setObdCurrentData();
		return "location";
	}
	
	private void setObdCurrentData(){
		HttpSession session=getSession();
		if (session.getAttribute("obdCurrentDataList")!=null) {
			return;
		}
		//缓存设备信息
		AccountInfo account=(AccountInfo) session.getAttribute(Constants.PORTAL_USER);
		Map<String, String> paramValues=caritClient.buildParamValues("platform.obd.currentData", "1.0");
		paramValues.put("accountId", String.valueOf(account.getId()));
		// 不需要签名的参数放后面
				paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		String dataList = caritClient.getHttpResponse(paramValues);
		session.setAttribute("obdCurrentDataList",dataList);
	}
	
	@RequestMapping(value="forget_pwd", method=RequestMethod.GET)
	public String forgetPwd(){
		return "get-back-pwd";
	}
	
	@RequestMapping(value="get_back_pwd", method=RequestMethod.GET)
	public @ResponseBody String getBackPwd(@RequestParam String email){
		Map<String, String> paramValues=caritClient.buildParamValues("account.getback.password", "1.0");
		paramValues.put("email", email);
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		return caritClient.getHttpResponse(paramValues);
	}
}
