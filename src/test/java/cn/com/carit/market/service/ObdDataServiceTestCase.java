package cn.com.carit.market.service;

import javax.annotation.Resource;

import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

import cn.com.carit.BaseTestCase;
import cn.com.carit.market.bean.obd.ObdData;
import cn.com.carit.market.bean.obd.PostData;
import cn.com.carit.market.service.obd.ObdDataService;

public class ObdDataServiceTestCase extends BaseTestCase<ObdData> {
	@Resource
	private ObdDataService service;
	
	@Test
	public void testSavePostData() throws Exception{
		String json="{\"data\":{\"08\":\"23\",\"09\":\"24\",\"04\":\"152047890\",\"05\":\"19\",\"15\":\"36\",\"06\":\"20\",\"16\":\"37\",\"07\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"01\":\"5\",\"11\":\"32\",\"02\":\"6\",\"12\":\"33\",\"03\":\"1800\",\"10\":\"25\"},\"dataType\":\"data\",\"error\":[\"010201\",\"010302\"],\"date\":\"1970-00-02 08:45:50\",\"location\":\"22.543099,114.057868\",\"deviceID\":\"B8EA553F\"}";
		ObjectMapper mapper = new ObjectMapper();
		PostData postData=mapper.readValue(json, PostData.class);
		service.savePostData(postData);
	}
}
