package cn.com.carit.market.web.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import cn.com.carit.market.bean.obd.ObdData;
import cn.com.carit.market.bean.obd.ObdDataResponse;
import cn.com.carit.market.bean.obd.PostData;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.DataGridModel;
import cn.com.carit.market.common.utils.JsonPage;
import cn.com.carit.market.service.obd.ObdDataService;

@Controller
@RequestMapping(value="obd")
public class ObdController {
	private final Logger log = Logger.getLogger(getClass());
	@Resource
	private ObdDataService obdDataService;
	
	/**
	 * 查询数据
	 * @param t
	 * @param dgm
	 * @return
	 */
	@RequestMapping(value="query/data",method=RequestMethod.GET)
	public @ResponseBody JsonPage<ObdData> queryData(ObdData t, DataGridModel dgm){
		return obdDataService.queryByExemple(t, dgm);
	}
	
	/**
	 * 保存数据
	 * @param req
	 * @return
	 */
	@RequestMapping(value="data/post", method=RequestMethod.POST)
	public @ResponseBody String postData(HttpServletRequest req){
		String result = "{error:0}";
		/* 读取数据 */
		try {
			BufferedReader reader= new BufferedReader(
					new InputStreamReader((ServletInputStream) req.getInputStream(), Constants.CHARACTER_ENCODING_UTF8));
			ObjectMapper mapper = new ObjectMapper();
			PostData postData=mapper.readValue(reader, PostData.class);
			obdDataService.savePostData(postData);
			reader.close();
		} catch (Exception e) {
			log.error("post data error.", e);
			result="{error:1}";
		}
		return result;
	}
	
	/**
	 * 获取最新数据
	 * @param deviceId
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="view/data/{deviceId}",method=RequestMethod.GET)
	public @ResponseBody ObdDataResponse viewData(@PathVariable String deviceId) throws Exception{
		return obdDataService.queryLastByDeviceId(deviceId);
	}
}
