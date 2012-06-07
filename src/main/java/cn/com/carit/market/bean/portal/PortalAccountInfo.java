package cn.com.carit.market.bean.portal;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

public class PortalAccountInfo {
	/**
	 * id
	 */
	private int id;
	/**
	 * email
	 */
	private String email;
	/**
	 * nickName
	 */
	private String nickName;
	/**
	 * photo
	 */
	private String photo;
	/**
	 * balance
	 */
	private Double balance;
	/**
	 * realName
	 */
	private String realName;
	/**
	 * lastLoginIp
	 */
	private String lastLoginIp;
	/**
	 * lastLoginTime
	 */
	private Date lastLoginTime;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getPhoto() {
		return photo;
	}
	public void setPhoto(String photo) {
		this.photo = photo;
	}
	public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	
}
