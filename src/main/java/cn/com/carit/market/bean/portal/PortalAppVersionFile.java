package cn.com.carit.market.bean.portal;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * AppVersionFile
 * Auto generated Code
 */
public class PortalAppVersionFile  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7273207673891490088L;
	@JsonIgnore
	private String local;
	/**
	 * id
	 */
	private int id;
	
	@JsonIgnore
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
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getNewFeatures() {
		return newFeatures;
	}
	public void setNewFeatures(String newFeatures) {
		this.newFeatures = newFeatures;
	}
	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	
}
