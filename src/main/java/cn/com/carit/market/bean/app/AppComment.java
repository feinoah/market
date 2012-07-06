package cn.com.carit.market.bean.app;

import java.io.Serializable;

import cn.com.carit.market.bean.BaseBean;

/**
 * AppComment
 * Auto generated Code
 */
public class AppComment extends BaseBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 684618201702296353L;
	/**
	 * appId
	 */
	private Integer appId;
	
	private String appName;
	
	private String enName;
	/**
	 * userId
	 */
	private Integer userId;
	
	private String userName;
	/**
	 * grade
	 */
	private Integer grade;
	/**
	 * comment
	 */
	private String comment;
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
	public void setUserId(Integer value) {
		this.userId = value;
	}
	public Integer getUserId() {
		return this.userId;
	}
	public void setGrade(Integer value) {
		this.grade = value;
	}
	public Integer getGrade() {
		return this.grade;
	}
	public void setComment(String value) {
		this.comment = value;
	}
	public String getComment() {
		return this.comment;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	public Integer getStatus() {
		return this.status;
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
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}

}
