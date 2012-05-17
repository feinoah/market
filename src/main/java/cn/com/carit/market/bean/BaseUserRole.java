package cn.com.carit.market.bean;

import java.io.Serializable;

/**
 * 用户角色表
 */
public class BaseUserRole implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 用户ID
	 */
	private Integer userId;

	/**
	 * 角色ID
	 */
	private Integer roleId;

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

}