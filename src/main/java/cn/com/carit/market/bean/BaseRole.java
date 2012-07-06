package cn.com.carit.market.bean;

import java.io.Serializable;
import java.util.Set;

import org.codehaus.jackson.annotate.JsonIgnore;

/**
 * 角色表
 */
public class BaseRole extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2380774340748595044L;

	/**
	 * 角色名称
	 */
	private String roleName;

	/**
	 * 角色描述
	 */
	private String roleDesc;
	
	/**添加时封装模块Id*/
	@JsonIgnore
	private String  modules;
	/**查询时关联模块集*/
	private Set<BaseModule> moduleSet;

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

	public String getModules() {
		return modules;
	}

	public void setModules(String modules) {
		this.modules = modules;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}
	
}