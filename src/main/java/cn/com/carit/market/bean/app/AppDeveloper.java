package cn.com.carit.market.bean.app;

import java.io.Serializable;

import cn.com.carit.market.bean.BaseBean;

public class AppDeveloper extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7841338613046855762L;
	
	private String name;
	private String website;
	private String email;
	private String remark;
	private Integer status;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getWebsite() {
		return website;
	}
	public void setWebsite(String website) {
		this.website = website;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
}
