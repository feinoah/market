package cn.com.carit.market.bean;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.common.jackjson.CustomDateTimeSerializer;

/**
 * 角色表
 */
public class BaseRole implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2380774340748595044L;

	/**
	 * 角色ID
	 */
	private int id;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDesc;
	
	private Date createTime;
	private Date updateTime;
	
	/**添加时封装模块Id*/
	private List<Integer> modules;
	/**查询时关联模块集*/
	private Set<BaseModule> moduleSet;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getRoleDesc() {
		return roleDesc;
	}

	public void setRoleDesc(String roleDesc) {
		this.roleDesc = roleDesc;
	}

	public Set<BaseModule> getModuleSet() {
		return moduleSet;
	}

	public void setModuleSet(Set<BaseModule> moduleSet) {
		this.moduleSet = moduleSet;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}
	@JsonSerialize(using = CustomDateTimeSerializer.class)
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public List<Integer> getModules() {
		return modules;
	}

	public void setModules(List<Integer> modules) {
		this.modules = modules;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}
	
}