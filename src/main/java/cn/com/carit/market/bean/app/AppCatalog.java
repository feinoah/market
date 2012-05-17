package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * AppCatalog
 * Auto generated Code
 */
public class AppCatalog  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2364638845600437095L;
	/**
	 * id
	 */
	private Integer id;
	/**
	 * name
	 */
	private String name;
	/**
	 * description
	 */
	private String description;
	/**
	 * displayIndex
	 */
	private Integer displayIndex;
	/**
	 * status
	 */
	private Byte status;
	/**
	 * createTime
	 */
	private Date createTime;
	/**
	 * updateTime
	 */
	private Date updateTime;

	public void setId(Integer value) {
		this.id = value;
	}
	public Integer getId() {
		return this.id;
	}
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
