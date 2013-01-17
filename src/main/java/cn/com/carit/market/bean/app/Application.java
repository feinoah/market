package cn.com.carit.market.bean.app;

import java.io.Serializable;

import org.codehaus.jackson.annotate.JsonIgnore;

import cn.com.carit.market.bean.BaseBean;

/**
 * Application Auto generated Code
 */
public class Application extends BaseBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 314936188348558691L;
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
	private Integer developer;
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
	
	private String features;
	private String enFeatures;

	/**
	 * 截图路径，以“;”分隔
	 */
	@JsonIgnore
	private String images;
	@JsonIgnore
	private String [] imageArray;
	/**
	 * status
	 */
	private Integer status;
	
	private String mainPic;
	
	private Integer local;
	/**软件包名（唯一），如微信:com.tencent.mm*/
	private String packageName;

	public String[] getImageList() {
		if (this.images != null && this.images.trim().length() > 0) {
			return this.images.trim().split(";");
		}
		return null;
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

	public Integer getDeveloper() {
		return developer;
	}

	public void setDeveloper(Integer developer) {
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

	public String getFeatures() {
		return features;
	}

	public void setFeatures(String features) {
		this.features = features;
	}

	public String getEnFeatures() {
		return enFeatures;
	}

	public void setEnFeatures(String enFeatures) {
		this.enFeatures = enFeatures;
	}

	public String[] getImageArray() {
		return imageArray;
	}

	public void setImageArray(String[] imageArray) {
		this.imageArray = imageArray;
	}

	public String getMainPic() {
		return mainPic;
	}

	public void setMainPic(String mainPic) {
		this.mainPic = mainPic;
	}

	public Integer getLocal() {
		return local;
	}

	public void setLocal(Integer local) {
		this.local = local;
	}

	public String getPackageName() {
		return packageName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

}
