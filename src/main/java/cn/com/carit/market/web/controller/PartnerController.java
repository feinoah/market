package cn.com.carit.market.web.controller;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.common.utils.JsonUtil;
import cn.com.carit.market.web.Partner;
import cn.com.carit.platform.client.CaritClient;
import cn.com.carit.platform.client.ClientUtils;
import cn.com.carit.platform.response.CommonRopResponse;

@Controller
@RequestMapping(value="partner")
public class PartnerController extends BaseController {
	
	private final String PARTNER="partner";
	@Resource
	private CaritClient caritClient;
	
	@RequestMapping(value="index", method=RequestMethod.GET)
	public String index(){
		return "partner/index";
	}
	
	@RequestMapping(value="login", method=RequestMethod.POST)
	public @ResponseBody
	String login(@RequestParam String firmName, @RequestParam String password) throws Exception {
		Partner p=(Partner) getSession().getAttribute(PARTNER);
		if (p!=null) {
			return JsonUtil.objToJson(p);
		}
		Map<String, String> paramValues=caritClient.buildParamValues("partner.logon", "1.0");
		paramValues.put("firmName", firmName);
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN, ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		paramValues.put("password", password);
		String json=caritClient.getHttpResponse(paramValues);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		try {
			p=JsonUtil.jsonToObject(json, Partner.class);
			addAttribute(PARTNER, p, true);
		} catch (Exception e) {
			logger.info(json);
		}
		return json;
	}
	
	@RequestMapping(value = "register", method = RequestMethod.POST)
	public @ResponseBody
	String register(@RequestParam String firmName,
			@RequestParam String password, @RequestParam String city,
			@RequestParam String addr, @RequestParam String contactPerson,
			@RequestParam String email, @RequestParam String phone) {
		Map<String, String> paramValues = caritClient.buildParamValues(
				"partner.register", "1.0");
		paramValues.put("firmName", firmName);
		paramValues.put("city", city);
		paramValues.put("addr", addr);
		paramValues.put("contactPerson", contactPerson);
		paramValues.put("email", email);
		paramValues.put("phone", phone);
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN,
				ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		paramValues.put("password", password);
		String json = caritClient.getHttpResponse(paramValues);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		return json;
	}
	
	@RequestMapping(value="logout", method=RequestMethod.GET)
	public String logout(){
		getSession().removeAttribute(PARTNER);
		return "partner/loginForm";
	}
	
	@RequestMapping(value="updatePwd", method=RequestMethod.POST)
	public @ResponseBody String updatePwd(@RequestParam("oldPassword")String oldPassword
			, @RequestParam("newPassword")String newPassword){
		Partner p=(Partner) getSession().getAttribute(PARTNER);
		Map<String, String> paramValues = caritClient.buildParamValues(
				"partner.update.pwd", "1.0");
		paramValues.put("id", String.valueOf(p.getId()));
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN,
						ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		paramValues.put("oldPassword", oldPassword);
		paramValues.put("password", newPassword);
		String json = caritClient.getHttpResponse(paramValues);
		
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		return json;
	}
	
	@RequestMapping(value="update", method=RequestMethod.POST)
	public @ResponseBody
	String update(@RequestParam(required = false) String city,
			@RequestParam(required = false) String addr,
			@RequestParam(required = false) String contactPerson,
			@RequestParam(required = false) String email,
			@RequestParam(required = false) String phone) {
		Partner p = (Partner) getSession().getAttribute(PARTNER);
		Map<String, String> paramValues = caritClient.buildParamValues(
				"partner.update", "1.0");
		paramValues.put("id", String.valueOf(p.getId()));
		boolean flag=false;
		if (StringUtils.hasText(city)) {
			paramValues.put("city", city);
			flag=true;
		}
		if (StringUtils.hasText(addr)) {
			paramValues.put("addr", addr);
			flag=true;
		}
		if (StringUtils.hasText(contactPerson)) {
			paramValues.put("contactPerson", contactPerson);
			flag=true;
		}
		if (StringUtils.hasText(email)) {
			paramValues.put("email", email);
			flag=true;
		}
		if (StringUtils.hasText(phone)) {
			paramValues.put("phone", phone);
			flag=true;
		}
		String json = "{\"successful\":true}";
		if (flag) {
			// 不需要签名的参数放后面
			paramValues.put(CaritClient.SYSTEM_PARAM_SIGN,
					ClientUtils.sign(paramValues, caritClient.getAppSecret()));
			json = caritClient.getHttpResponse(paramValues);
			if (logger.isDebugEnabled()) {
				logger.debug(json);
			}
			try {
				JsonUtil.jsonToObject(json, CommonRopResponse.class);
				// 更新缓存
				if (StringUtils.hasText(city)) {
					p.setCity(city);
				}
				if (StringUtils.hasText(addr)) {
					p.setAddr(addr);
				}
				if (StringUtils.hasText(contactPerson)) {
					p.setContactPerson(contactPerson);
				}
				if (StringUtils.hasText(email)) {
					p.setEmail(email);
				}
				if (StringUtils.hasText(phone)) {
					p.setPhone(phone);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		return json;
	}
	
	@RequestMapping(value="device_list", method=RequestMethod.GET)
	public String deviceList(){
		return "partner/device-list";
	}
	
	@RequestMapping(value="query/devices", method=RequestMethod.GET)
	public @ResponseBody String qeruyDevices(@RequestParam(required=false) String email
			, @RequestParam(required=false) String nickName
			, @RequestParam(required=false) int page
			, @RequestParam(required=false) int rows){
		Partner p = (Partner) getSession().getAttribute(PARTNER);
		Map<String, String> paramValues = caritClient.buildParamValues(
				"partner.query.devices", "1.0");
		paramValues.put("partnerId", String.valueOf(p.getId()));
		if (StringUtils.hasText(email)) {
			paramValues.put("email", email);
		}
		if (StringUtils.hasText(nickName)) {
			paramValues.put("nickName", nickName);
		}
		if (page>0) {
			paramValues.put("page", String.valueOf(page));
		}
		if (rows>0) {
			paramValues.put("rows", String.valueOf(rows));
		}
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN,
				ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		String json = caritClient.getHttpResponse(paramValues);
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		return json;
	}
	
	@RequestMapping(value="obd", method=RequestMethod.GET)
	public String obd(){
		return "partner/obd";
	}
	
	@RequestMapping(value="bound/{deviceId}", method=RequestMethod.GET)
	public @ResponseBody String boundDevice(@PathVariable String deviceId) {
		Partner p=(Partner) getSession().getAttribute(PARTNER);
		Map<String, String> paramValues = caritClient.buildParamValues(
				"partner.device.bingding", "1.0");
		paramValues.put("partnerId", String.valueOf(p.getId()));
		paramValues.put("deviceId", deviceId);
		// 不需要签名的参数放后面
		paramValues.put(CaritClient.SYSTEM_PARAM_SIGN,
						ClientUtils.sign(paramValues, caritClient.getAppSecret()));
		String json = caritClient.getHttpResponse(paramValues);
		
		if (logger.isDebugEnabled()) {
			logger.debug(json);
		}
		return json;
	}
}
