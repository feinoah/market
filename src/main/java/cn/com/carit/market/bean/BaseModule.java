package cn.com.carit.market.bean;

import java.io.Serializable;

/**
 * 系统模块表
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-5-12
 */
public class BaseModule extends BaseBean implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int MAX_SYSTEM_MODULE_ID=100;
	
	/**
	 * 模块名称
	 */
	private String moduleName;

	/**
	 * 模块URL
	 */
	private String moduleUrl;

	/**
	 * 父模块ID
	 */
	private Integer parentId;
	
	/**
	 * 层级
	 */
	private Integer level;

	/**
	 * 是否展开
	 */
	private Boolean expanded;

	/**
	 * 显示顺序
	 */
	private Short displayIndex;

	/**
	 * 是否显示 
	 */
	private Boolean display;


	/**
	 * 图标或者样式
	 */
	private String iconCss;

	/**
	 * 节点说明
	 */
	private String information;
	
	public String getModuleName() {
		return moduleName;
	}

	public void setModuleName(String moduleName) {
		this.moduleName = moduleName;
	}

	public String getModuleUrl() {
		return moduleUrl;
	}

	public void setModuleUrl(String moduleUrl) {
		this.moduleUrl = moduleUrl;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public Boolean getExpanded() {
		return expanded;
	}

	public void setExpanded(Boolean expanded) {
		this.expanded = expanded;
	}

	public Short getDisplayIndex() {
		return displayIndex;
	}

	public void setDisplayIndex(Short displayIndex) {
		this.displayIndex = displayIndex;
	}

	public Boolean getDisplay() {
		return display;
	}

	public void setDisplay(Boolean display) {
		this.display = display;
	}

	public String getIconCss() {
		return iconCss;
	}

	public void setIconCss(String iconCss) {
		this.iconCss = iconCss;
	}

	public String getInformation() {
		return information;
	}

	public void setInformation(String information) {
		this.information = information;
	}

	@Override
	public int hashCode() {
		return Integer.valueOf(id).hashCode();
	}
}