package cn.com.carit.market.bean.portal;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateSerializer;

/**
 * Application Auto generated Code
 */
public class PortalApplication implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 314936188348558691L;
	
	@JsonIgnore
	private String local;
	/**
	 * id
	 */
	private int id;
	/**
	 * appName
	 */
	private String appName;
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
	
	private String developerWebsite;
	
	private String developerEmail;
	/**
	 * catalogId
	 */
	@JsonIgnore
	private Integer catalogId;
	
	/**
	 * 分类名称
	 */
	private String catalogName;
	/**
	 * size
	 */
	private String size;
	/**
	 * 应用文件路径
	 */
	private String appFilePath;
	/**
	 * 适用平台
	 */
	private String platform;
	/**
	 * 支持语言
	 */
	private String supportLanguages;
	/**
	 * price
	 */
	private Double price;
	/**
	 * 下载次数
	 */
	private Integer downCount;
	/**
	 * 应用评级
	 */
	private Integer appLevel;
	/**
	 * 应用描述
	 */
	private String description;
	/**
	 * 权限描述
	 */
	private String permissionDesc;
	
	/**
	 * 特性
	 */
	private String features;
	/**
	 * 截图路径，以“;”分隔，Json中不返回此字段，将处理成数组 imageList 返回
	 */
	@JsonIgnore
	private String images;
	/**
	 * 更新日期
	 */
	private Date updateTime;
	
	@JsonIgnore
	private Integer status;

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

	public Integer getCatalogId() {
		return catalogId;
	}

	public void setCatalogId(Integer catalogId) {
		this.catalogId = catalogId;
	}

	public String getCatalogName() {
		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		this.catalogName = catalogName;
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

	public String getSupportLanguages() {
		return supportLanguages;
	}

	public void setSupportLanguages(String supportLanguages) {
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

	public String getPermissionDesc() {
		return permissionDesc;
	}

	public void setPermissionDesc(String permissionDesc) {
		this.permissionDesc = permissionDesc;
	}

	public String getImages() {
		return images;
	}

	public void setImages(String images) {
		this.images = images;
	}

	public String[] getImageList() {
		if (this.images != null && this.images.trim().length() > 0) {
			return this.images.trim().split(";");
		}
		return null;
	}

	public String getLocal() {
		return local;
	}

	public void setLocal(String local) {
		this.local = local;
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
	
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getDeveloperWebsite() {
		return developerWebsite;
	}

	public void setDeveloperWebsite(String developerWebsite) {
		this.developerWebsite = developerWebsite;
	}

	public String getDeveloperEmail() {
		return developerEmail;
	}

	public void setDeveloperEmail(String developerEmail) {
		this.developerEmail = developerEmail;
	}
	
}
