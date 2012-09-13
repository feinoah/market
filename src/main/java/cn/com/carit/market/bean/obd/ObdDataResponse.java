package cn.com.carit.market.bean.obd;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

public class ObdDataResponse {
	private String dataType="data";
	private Map<Integer, Integer> data=new HashMap<Integer, Integer>(16);
	private String location;
	private String deviceID;
	@JsonSerialize(using=CustomDateTimeSerializer.class)
	private Date date;
	private Object [] error;
//	private String error;
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
	public Object[] getError() {
		return error;
	}
	public void setError(Object[] error) {
		this.error = error;
	}
//	
//	public String getError() {
//		return error;
//	}
//	public void setError(String error) {
//		this.error = error;
//	}

}
