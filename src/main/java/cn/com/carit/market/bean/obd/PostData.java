package cn.com.carit.market.bean.obd;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.annotate.JsonDeserialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeDeserializer;

public class PostData {
	private String dataType;
	private Map<Integer, Integer> data=new HashMap<Integer, Integer>(16);
	private String location;
	private String deviceID;
	@JsonDeserialize(using=CustomDateTimeDeserializer.class)
	private Date date;
	private String [] error;
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public Map<Integer, Integer> getData() {
		return data;
	}
	public void setData(Map<Integer, Integer> data) {
		this.data = data;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public String getDeviceID() {
		return deviceID;
	}
	public void setDeviceID(String deviceID) {
		this.deviceID = deviceID;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public String[] getError() {
		return error;
	}
	public void setError(String[] error) {
		this.error = error;
	}
	public static void main(String[] args) {
		String json="{\"data\":{\"08\":\"23\",\"09\":\"24\",\"04\":\"152047890\",\"05\":\"19\",\"15\":\"36\",\"06\":\"20\",\"16\":\"37\",\"07\":\"5653\",\"13\":\"34\",\"14\":\"35\",\"01\":\"5\",\"11\":\"32\",\"02\":\"6\",\"12\":\"33\",\"03\":\"1800\",\"10\":\"25\"},\"dataType\":\"data\",\"error\":[\"010201\",\"010302\"],\"date\":\"1970-00-02 08:45:50\",\"location\":\"22.543099,114.057868\",\"deviceID\":\"B8EA553F\"}";
		ObjectMapper mapper = new ObjectMapper();
		try {
			PostData data=mapper.readValue(json, PostData.class);
			System.out.println(data);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	@Override
	public String toString() {
		return "PostData [dataType=" + dataType + ", data=" + data
				+ ", location=" + location + ", deviceID=" + deviceID
				+ ", date=" + date + ", error=" + Arrays.toString(error) + "]";
	}
	
}
