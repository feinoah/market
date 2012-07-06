package cn.com.carit.market.bean.app;

import java.io.Serializable;

import cn.com.carit.market.bean.BaseBean;

/**
 * AppVersionFile
 * Auto generated Code
 */
public class AppVersionFile extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7273207673891490088L;
	/**
	 * appId
	 */
	private Integer appId;
	
	private String appName;
	
	private String enName;
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
	
	private String enNewFeatures;
	/**
	 * status
	 */
	private Integer status;
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
	public String getEnNewFeatures() {
		return enNewFeatures;
	}
	public void setEnNewFeatures(String enNewFeatures) {
		this.enNewFeatures = enNewFeatures;
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
