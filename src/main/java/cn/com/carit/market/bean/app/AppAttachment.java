package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * AppAttachment
 * Auto generated Code
 */
public class AppAttachment  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7655349518394557028L;
	/**
	 * id
	 */
	private int id;
	/**
	 * appId
	 */
	private Integer appId;
	/**
	 * filePath
	 */
	private String filePath;
	/**
	 * status
	 */
	private Integer status;
	/**
	 * createTime
	 */
	private Date createTime;
	/**
	 * updateTime
	 */
	private Date updateTime;

	public void setId(int value) {
		this.id = value;
	}
	public int getId() {
		return this.id;
	}
	public void setAppId(Integer value) {
		this.appId = value;
	}
	public Integer getAppId() {
		return this.appId;
	}
	public void setFilePath(String value) {
		this.filePath = value;
	}
	public String getFilePath() {
		return this.filePath;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	public Integer getStatus() {
		return this.status;
	}
	public void setCreateTime(Date value) {
		this.createTime = value;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTime() {
		return this.createTime;
	}
	public void setUpdateTime(Date value) {
		this.updateTime = value;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateTime() {
		return this.updateTime;
	}

}
