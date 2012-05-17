package cn.com.carit.market.bean;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * easyUI菜单机构树
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-5-13
 */
public class Tree {
	private int id;
	private String text;
	private String iconCls;
	private String state;
	private Map<String, String> attributes=new HashMap<String, String>();
	private List<Tree> children;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public Map<String, String> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<String, String> attributes) {
		this.attributes = attributes;
	}
	public List<Tree> getChildren() {
		return children;
	}
	public void setChildren(List<Tree> children) {
		this.children = children;
	}
}
