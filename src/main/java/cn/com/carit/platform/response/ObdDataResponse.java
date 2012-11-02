package cn.com.carit.platform.response;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ObdDataResponse {
	private String dataType="data";
	private Map<Integer, Integer> data=new HashMap<Integer, Integer>();
	private String location;
	private Date date;
	private Object [] error;
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

}
