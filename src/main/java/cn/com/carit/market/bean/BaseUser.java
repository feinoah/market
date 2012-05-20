package cn.com.carit.market.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * 用户表
 */
public class BaseUser implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private int id;

	/**
	 * 电子邮件地址
	 */
	private String email;
	
	/**
	 * 密码 MD5Util.md5Hex(MD5Util.md5Hex(password){email}disturbStr)
	 * md5(md5(pwd)+email+干扰串)
	 */
	@JsonIgnore
	private String password;
	
	/**
	 * 昵称
	 */
	private String nickName;


	/**
	 * 用户真实姓名
	 */
	private String realName;

	/**
	 * 性别 0：女 1：男 2：保密
	 */
	private Byte gender;


	/**
	 * 手机
	 */
	private Long mobile;

	/**
	 * 办公电话
	 */
	private String officePhone;

	/**
	 * 上次登录时间
	 */
	private Date lastLoginTime;

	/**
	 * 上次登录IP地址
	 */
	private String lastLoginIp;

	/**
	 * 备注
	 */
	private String remark;
	
	/**状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）*/
	private Byte status;
	
	/**
	 * 创建时间
	 */
	@JsonIgnore
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
	
	/**查看用户是缓存角色集合*/
	private Set<BaseRole> roleSet;
	
	@JsonIgnore
	private Date lastLoginTimeStart;
	
	@JsonIgnore
	private Date lastLoginTimeEnd;
	
	/**封装表单角色信息*/
	@JsonIgnore
	private String roles;
	
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
	public String getPassword()  {
		return password;
	}
	public void setPassword(String password) {
		this.password=password;
	}
	public String getNickName() {
		return nickName;
	}
	public void setNickName(String nickName) {
		this.nickName = nickName;
	}
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public Byte getGender() {
		return gender;
	}
	public void setGender(Byte gender) {
		this.gender = gender;
	}
	public Long getMobile() {
		return mobile;
	}
	public void setMobile(Long mobile) {
		this.mobile = mobile;
	}
	public String getOfficePhone() {
		return officePhone;
	}
	public void setOfficePhone(String officePhone) {
		this.officePhone = officePhone;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getLastLoginTime() {
		return lastLoginTime;
	}
	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}
	public String getLastLoginIp() {
		return lastLoginIp;
	}
	public void setLastLoginIp(String lastLoginIp) {
		this.lastLoginIp = lastLoginIp;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getStatus() {
		return status;
	}
	/**状态：0 停用；0x1 启用；0x2：禁止登录（密码错误次数过多，一定时间内禁止登录）*/
	public void setStatus(Byte status) {
		this.status = status;
	}
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
	/**查看用户是缓存角色集合*/
	public Set<BaseRole> getRoleSet() {
		return roleSet;
	}
	public void setRoleSet(Set<BaseRole> roleSet) {
		this.roleSet = roleSet;
	}
	public Date getLastLoginTimeStart() {
		return lastLoginTimeStart;
	}
	public void setLastLoginTimeStart(Date lastLoginTimeStart) {
		this.lastLoginTimeStart = lastLoginTimeStart;
	}
	public Date getLastLoginTimeEnd() {
		return lastLoginTimeEnd;
	}
	public void setLastLoginTimeEnd(Date lastLoginTimeEnd) {
		this.lastLoginTimeEnd = lastLoginTimeEnd;
	}
	public String getRoles() {
		return roles;
	}
	public void setRoles(String roles) {
		this.roles = roles;
	}
}