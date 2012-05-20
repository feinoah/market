package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * AppVersionFile
 * Auto generated Code
 */
public class AppVersionFile  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7273207673891490088L;
	/**
	 * id
	 */
	private int id;
	/**
	 * appId
	 */
	private Integer appId;
	/**
	 * version
	 */
	private String version;
	/**
	 * size
	 */
	private String size;
	/**
	 * filePath
	 */
	private String filePath;
	/**
	 * newFeatures
	 */
	private String newFeatures;
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
	public void setVersion(String value) {
		this.version = value;
	}
	public String getVersion() {
		return this.version;
	}
	public void setSize(String value) {
		this.size = value;
	}
	public String getSize() {
		return this.size;
	}
	public void setFilePath(String value) {
		this.filePath = value;
	}
	public String getFilePath() {
		return this.filePath;
	}
	public void setNewFeatures(String value) {
		this.newFeatures = value;
	}
	public String getNewFeatures() {
		return this.newFeatures;
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
