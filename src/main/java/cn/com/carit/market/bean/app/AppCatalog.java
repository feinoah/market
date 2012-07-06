package cn.com.carit.market.bean.app;

import java.io.Serializable;

import cn.com.carit.market.bean.BaseBean;

/**
 * AppCatalog
 * Auto generated Code
 */
public class AppCatalog extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2364638845600437095L;
	/**
	 * name
	 */
	private String name;
	
	private String enName;
	/**
	 * description
	 */
	private String description;
	
	private String enDescription;
	/**
	 * displayIndex
	 */
	private Integer displayIndex;
	/**
	 * status
	 */
	private Byte status;

	public void setName(String value) {
		this.name = value;
	}
	public String getName() {
		return this.name;
	}
	public void setDescription(String value) {
		this.description = value;
	}
	public String getDescription() {
		return this.description;
	}
	public void setDisplayIndex(Integer value) {
		this.displayIndex = value;
	}
	public Integer getDisplayIndex() {
		return this.displayIndex;
	}
	public void setStatus(Byte value) {
		this.status = value;
	}
	public Byte getStatus() {
		return this.status;
	}
	public String getEnName() {
		return enName;
	}
	public void setEnName(String enName) {
		this.enName = enName;
	}
	public String getEnDescription() {
		return enDescription;
	}
	public void setEnDescription(String enDescription) {
		this.enDescription = enDescription;
	}

}
