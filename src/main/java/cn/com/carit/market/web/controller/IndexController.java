package cn.com.carit.market.web.controller;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.app.AccountInfo;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.JsonUtil;
import cn.com.carit.market.common.utils.StringUtil;
import cn.com.carit.market.web.CacheManager;
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
	 * @throws Exception 
	 */
	@RequestMapping(value="query/locations/{deviceId}/{accountId}", method=RequestMethod.GET)
	@ResponseBody
	public String queryLocations(@PathVariable String deviceId
			, @PathVariable String accountId
			, @RequestParam(required=false) String type
			, @RequestParam(required=false) String startTime
			, @RequestParam(required=false) String endTime
			, @RequestParam(required=false) int page
			, @RequestParam(required=false) int rows) throws Exception{
		Map<String, String> paramValues=caritClient.buildParamValues("platform.location.search", "2.0");
		paramValues.put("deviceId", deviceId);
		paramValues.put("accountId", accountId);
		
		if (StringUtils.hasText(type)) {
			paramValues.put("type", type);
		}
		if (page>0) {
			paramValues.put("page", String.valueOf(page));
		}
		if (rows>0) {
			paramValues.put("rows", String.valueOf(rows));
		}
		if (StringUtils.hasText(startTime)) {
			paramValues.put("startTime", String.valueOf(StringUtil.strToDate(startTime, Constants.DATE_HOUR_MIN_FORMATTER).getTime()));
		}
		if (StringUtils.hasText(endTime)) {
			paramValues.put("endTime", String.valueOf(StringUtil.strToDate(endTime, Constants.DATE_HOUR_MIN_FORMATTER).getTime()));
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
		return "profile";
	}
	
	@RequestMapping(value="obd", method=RequestMethod.GET)
	public String obdIndex(){
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
	@RequestMapping(value="obd/search/{deviceId}/{accountId}/{index}", method=RequestMethod.GET)
	public @ResponseBody String searchObdData(@PathVariable String deviceId
			, @PathVariable String accountId
			, @PathVariable String index
			, @RequestParam(required=false) int page
			, @RequestParam(required=false) int rows
			, @RequestParam(required=false) Date startTime
			, @RequestParam(required=false) Date endTime){
		Map<String, String> paramValues=caritClient.buildParamValues("platform.obd.search", "1.0");
		
		paramValues.put("deviceId", deviceId);
		paramValues.put("accountId", accountId);
		paramValues.put("index", index);
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
		return "location";
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
	
	@RequestMapping(value="bluetooth", method=RequestMethod.GET)
	public String bluetoothContact() throws Exception{
		AccountInfo t=(AccountInfo) getSession().getAttribute(Constants.PORTAL_USER);
		Map<String, String> paramValues=caritClient.buildParamValues("platform.bluetooth.contact.query", "1.0");
		paramValues.put("email", t.getEmail());
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		addAttribute("bluetoothList", JsonUtil.jsonToList(caritClient.getHttpResponse(paramValues)), false);
		return "bluetooth-contact";
	}
	
	@RequestMapping(value="bluetooth/cantact/query", method=RequestMethod.GET)
	public @ResponseBody
	String queryBluetoothCantact(@RequestParam String email,
			@RequestParam String deviceId,
			@RequestParam(required = false) String bluetoothId,
			@RequestParam(required = false) String name,
			@RequestParam(required = false) String nameKey,
			@RequestParam(required = false) String num,
			@RequestParam(required = false) int page,
			@RequestParam(required = false) int rows) {
		Map<String, String> paramValues=caritClient.buildParamValues("platform.bluetooth.contact.sync", "1.0");
		paramValues.put("email", email);
		paramValues.put("deviceId", deviceId);
		if (StringUtils.hasText(bluetoothId)) {
			paramValues.put("bluetoothId", bluetoothId);
		}
		if (StringUtils.hasText(name)) {
			paramValues.put("callName", name);
		}
		if (StringUtils.hasText(nameKey)) {
			paramValues.put("callNameKey", nameKey);
		}
		if (StringUtils.hasText(num)) {
			paramValues.put("callNum", num);
		}
		if (page>0) {
			paramValues.put("page", String.valueOf(page));
		}
		if (rows>0) {
			paramValues.put("rows", String.valueOf(rows));
		}
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		return caritClient.getHttpResponse(paramValues);
	}
	
	@RequestMapping(value="nav", method=RequestMethod.GET)
	public String navIndex(){
		addAttribute("catalogList", CacheManager.getInstance().getNavCatalogs(), false);
		return "nav_index";
	}
}
