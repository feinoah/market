package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * Application Auto generated Code
 */
public class Application implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 314936188348558691L;
	/**
	 * id
	 */
	private int id;
	/**
	 * appName
	 */
	private String appName;
	/**
	 * enName
	 */
	private String enName;
	/**
	 * version
	 */
	private String version;
	/**
	 * icon
	 */
	private String icon;
	/**
	 * bigIcon
	 */
	private String bigIcon;
	/**
	 * developer
	 */
	private String developer;
	/**
	 * catalogId
	 */
	private Integer catalogId;
	/**
	 * size
	 */
	private String size;
	/**
	 * appFilePath
	 */
	private String appFilePath;
	/**
	 * platform
	 */
	private String platform;
	/**
	 * supportLanguages
	 */
	private Integer supportLanguages;
	/**
	 * price
	 */
	private Double price;
	/**
	 * downCount
	 */
	private Integer downCount;
	/**
	 * appLevel
	 */
	private Integer appLevel;
	/**
	 * description
	 */
	private String description;
	private String enDescription;
	/**
	 * permissionDesc
	 */
	private String permissionDesc;
	private String enPermissionDesc;

	/**
	 * 截图路径，以“;”分隔
	 */
	@JsonIgnore
	private String images;
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

	/**
	 * 版本文件
	 */
	@JsonIgnore
	private AppVersionFile appVersionFile;

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public String[] getImageList() {
		if (this.images != null && this.images.trim().length() > 0) {
			return this.images.trim().split(";");
		}
		return null;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getBigIcon() {
		return bigIcon;
	}

	public void setBigIcon(String bigIcon) {
		this.bigIcon = bigIcon;
	}

	public String getDeveloper() {
		return developer;
	}

	public void setDeveloper(String developer) {
		this.developer = developer;
	}

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getAppFilePath() {
		return appFilePath;
	}

	public void setAppFilePath(String appFilePath) {
		this.appFilePath = appFilePath;
	}

	public String getPlatform() {
		return platform;
	}

	public void setPlatform(String platform) {
		this.platform = platform;
	}

	public Integer getSupportLanguages() {
		return supportLanguages;
	}

	public void setSupportLanguages(Integer supportLanguages) {
		this.supportLanguages = supportLanguages;
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getDownCount() {
		return downCount;
	}

	public void setDownCount(Integer downCount) {
		this.downCount = downCount;
	}

	public Integer getAppLevel() {
		return appLevel;
	}

	public void setAppLevel(Integer appLevel) {
		this.appLevel = appLevel;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getEnDescription() {
		return enDescription;
	}

	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getEnPermissionDesc() {
		return enPermissionDesc;
	}

	public void setEnPermissionDesc(String enPermissionDesc) {
		this.enPermissionDesc = enPermissionDesc;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public AppVersionFile getAppVersionFile() {
		return appVersionFile;
	}

	public void setAppVersionFile(AppVersionFile appVersionFile) {
		this.appVersionFile = appVersionFile;
	}

}
