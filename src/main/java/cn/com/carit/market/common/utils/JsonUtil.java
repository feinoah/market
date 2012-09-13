package cn.com.carit.market.common.utils;

import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.util.StringUtils;

public class JsonUtil {

	
	@SuppressWarnings("unchecked")
	public static Map<Integer,Integer> jsonToMap(String json) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, Map.class);
	}
	
	@SuppressWarnings("rawtypes")
	public static String mapToStr(Map map) throws Exception{
		ObjectMapper mapper = new ObjectMapper();
		return mapper.writeValueAsString(map);
	}
	
	public static String[] jsonToStrArray(String json) throws Exception {
		if (!StringUtils.hasText(json)) {
			json="[]";
		}
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(json, String[].class);
	}
	
	public static String arrayToStr(String [] array) throws Exception{
		if (array!=null && array.length>0) {
			ObjectMapper mapper = new ObjectMapper();
			return mapper.writeValueAsString(array);
		}
		return "[]";
	}
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws Exception {
		String json="{\"8\":\"23\",\"9\":\"24\",\"4\":\"152047890\",\"5\":\"19\",\"15\":\"36\",\"6\":\"20\",\"16\":\"37\",\"7\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"1\":\"5\",\"11\":\"32\",\"2\":\"6\",\"12\":\"33\",\"3\":\"1800\",\"10\":\"25\"}";
		Map<Integer, Integer> map=jsonToMap(json);
		for (Entry<Integer,Integer> e: map.entrySet()) {
			System.out.println(e.getKey()+":"+e.getValue());
		}
		System.out.println(mapToStr(map));
		String json2="[\"010201\",\"010302\"]";
		String [] array=jsonToStrArray(json2);
		for(String str:array){
			System.out.println(str);
		}
		System.out.println(arrayToStr(array));
	}

}
