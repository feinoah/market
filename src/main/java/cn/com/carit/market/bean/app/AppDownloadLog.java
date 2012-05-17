package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * AppDownloadLog
 * Auto generated Code
 */
public class AppDownloadLog  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1966861193981582254L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * accountId
	 */
	private Integer accountId;
	/**
	 * appId
	 */
	private Integer appId;
	/**
	 * downloadTime
	 */
	private Date downloadTime;

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getId() {
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
	public void setDownloadTime(Date value) {
		this.downloadTime = value;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getDownloadTime() {
		return this.downloadTime;
	}

}
