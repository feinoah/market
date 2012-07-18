package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * AppDownloadLog
 * Auto generated Code
 */
public class AppDownloadLog implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1966861193981582254L;
	/**
	 * id
	 */
	private int id;
	/**
	 * accountId
	 */
	private Integer accountId;
	
	private String userName;
	/**
	 * appId
	 */
	private Integer appId;
	
	private String version;

	private String appName;
	
	private String enName;
	
	/**
	 * downloadTime
	 */
	private Date downloadTime;

	public void setId(int value) {
		this.id = value;
	}
	public int getId() {
		return this.id;
	}
	public void setAccountId(Integer value) {
		this.accountId = value;
	}
	public Integer getAccountId() {
		return this.accountId;
	}
	public void setAppId(Integer value) {
		this.appId = value;
	}
	public Integer getAppId() {
		return this.appId;
	}
	
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setDownloadTime(Date value) {
		this.downloadTime = value;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getDownloadTime() {
		return this.downloadTime;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}

}
