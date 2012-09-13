package cn.com.carit.market.bean.obd;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateDeserializer;
import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * OBD 数据对象
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * 2012-9-10
 */
public class ObdData {

	@JsonIgnore
	private int id;
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	private Date date;
	@JsonIgnore
	@JsonDeserialize(using=CustomDateDeserializer.class)
	private Date startDate;
	@JsonIgnore
	@JsonDeserialize(using=CustomDateDeserializer.class)
	private Date endDate;
	private String deviceId;
	private String location;
	private String error;
	@JsonIgnore
	private Date createTime;
	private int [] values=new int[19];
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public String getDeviceId() {
		return deviceId;
	}
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public int[] getValues() {
		return values;
	}
	public void setValues(int[] values) {
		this.values = values;
	}
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	
}
