package cn.com.carit.market.bean;

import java.io.Serializable;

/**
 * 角色模块关联表
 */
public class BaseRoleModule implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1661315951364618061L;


	/**
	 * 角色ID
	 */
	private Integer roleId;

	/**
	 * 模块ID
	 */
	private Integer moduleId;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getModuleId() {
		return moduleId;
	}

	public void setModuleId(Integer moduleId) {
		this.moduleId = moduleId;
	}

	
}