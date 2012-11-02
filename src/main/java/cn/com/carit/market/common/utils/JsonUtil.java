package cn.com.carit.market.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

public class JsonUtil {

	
public static final ObjectMapper MAPPER = new ObjectMapper();
	
	@SuppressWarnings("unchecked")
	public static <K, V> Map<K,V> jsonToMap(String json) throws Exception{
		return MAPPER.readValue(json, Map.class);
	}
	
	public static <K, V> String mapToStr(Map<K, V> map) throws Exception{
		return MAPPER.writeValueAsString(map);
	}
	
	public static Object [] jsonToStrArray(String json) throws Exception {
		if (json==null || json.isEmpty()) {
			json="[]";
		}
		return MAPPER.readValue(json, Object[].class);
	}
	
	public static String arrayToStr(Object [] array) throws Exception{
		if (array!=null && array.length>0) {
			return MAPPER.writeValueAsString(array);
		}
		return "[]";
	}
	
	public static <T> Object jsonToObject(String json, Class<T> clazz) throws Exception{
		return MAPPER.readValue(json, clazz);
	}
	
	public static <T> List<T> jsonToList(String json) throws Exception {
		return MAPPER.readValue(json, new TypeReference<List<T>>() {
			
		});
	}
	
	/**
	 * @param args
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public static void main(String[] args) throws Exception {
		String json="{\"8\":\"23\",\"9\":\"24\",\"4\":\"152047890\",\"5\":\"19\",\"15\":\"36\",\"6\":\"20\",\"16\":\"37\",\"7\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"1\":\"5\",\"11\":\"32\",\"2\":\"6\",\"12\":\"33\",\"3\":\"1800\",\"10\":\"25\"}";
		Map<String, String> map=jsonToMap(json);
		for (Entry<String,String> e: map.entrySet()) {
			System.out.println(e.getKey()+":"+e.getValue());
		}
		System.out.println(mapToStr(map));
		String json2="[\"010201\",\"010302\"]";
		Object [] array=jsonToStrArray(json2);
		for(Object str:array){
			System.out.println(str);
		}
		System.out.println(arrayToStr(array));
		System.out.println("   \t\t\r\nlll".replaceAll("\\W", ""));
	}

}
