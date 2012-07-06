package cn.com.carit.market.bean.portal;

import java.util.Date;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateSerializer;
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
	 * gender
	 */
	private Byte gender;
	/**
	 * birthday
	 */
	private Date birthday;
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
	 * idCard
	 */
	private String idCard;
	/**
	 * officePhone
	 */
	private String officePhone;
	/**
	 * mobile
	 */
	private String mobile;
	/**
	 * address
	 */
	private String address;
	/**
	 * lastLoginIp
	 */
	private String lastLoginIp;
	/**
	 * lastLoginTime
	 */
	private Date lastLoginTime;
	/**
	 * status
	 */
	private Byte status;

	public void setId(int value) {
		this.id = value;
	}
	public int getId() {
		return this.id;
	}
	public void setEmail(String value) {
		this.email = value;
	}
	public String getEmail() {
		return this.email;
	}
	public void setNickName(String value) {
		this.nickName = value;
	}
	public String getNickName() {
		return this.nickName;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public Byte getGender() {
		return this.gender;
	}
	public void setBirthday(Date value) {
		this.birthday = value;
	}
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getBirthday() {
		return this.birthday;
	}
	public void setPhoto(String value) {
		this.photo = value;
	}
	public String getPhoto() {
		return this.photo;
	}
	public void setBalance(Double value) {
		this.balance = value;
	}
	public Double getBalance() {
		return this.balance;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	public String getRealName() {
		return this.realName;
	}
	public void setIdCard(String value) {
		this.idCard = value;
	}
	public String getIdCard() {
		return this.idCard;
	}
	public void setOfficePhone(String value) {
		this.officePhone = value;
	}
	public String getOfficePhone() {
		return this.officePhone;
	}
	public void setMobile(String value) {
		this.mobile = value;
	}
	public String getMobile() {
		return this.mobile;
	}
	public void setAddress(String value) {
		this.address = value;
	}
	public String getAddress() {
		return this.address;
	}
	public void setLastLoginIp(String value) {
		this.lastLoginIp = value;
	}
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
	public void setLastLoginTime(Date value) {
		this.lastLoginTime = value;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setStatus(Byte value) {
		this.status = value;
	}
	public Byte getStatus() {
		return this.status;
	}
}
