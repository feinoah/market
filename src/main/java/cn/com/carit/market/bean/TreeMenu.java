package cn.com.carit.market.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

public class TreeMenu  implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5854311479633135600L;
	private List<BaseModule> list;

	public TreeMenu(List<BaseModule> list) {
		this.list = list;
	}

	/**
	 * 组合json
	 * 
	 * @param list
	 * @param node
	 */
	private Tree getNodeJson(List<BaseModule> list, BaseModule node) {
		Tree tree = new Tree();
		tree.setId(node.getId());
		tree.setText(node.getModuleName());
		tree.setIconCls(node.getIconCss());
		tree.setState(node.getExpanded()?"open":"close");
//		tree.setUrl(node.getModuleUrl());
		String url=node.getModuleUrl();
		if (StringUtils.hasText(url)) {
			Map<String, String> attributes=new HashMap<String, String>();
			attributes.put("url", url.trim());
			tree.setAttributes(attributes);
		}
		tree.setChildren(new ArrayList<Tree>());
		if (list == null) {
			// 防止没有权限菜单时
			return tree;
		}
		if (hasChild(list, node)) {
			List<Tree> lt = new ArrayList<Tree>();
			List<BaseModule> childList = getChildList(list, node);
			Iterator<BaseModule> it = childList.iterator();
			while (it.hasNext()) {
				BaseModule module = (BaseModule) it.next();
				// 递归
				lt.add(getNodeJson(list, module));
			}
			tree.setChildren(lt);
		}
		return tree;
	}

	/**
	 * 判断是否有子节点
	 */
	private boolean hasChild(List<BaseModule> list, BaseModule node) {
		return getChildList(list, node).size() > 0 ? true : false;
	}

	/**
	 * 得到子节点列表
	 */
	private List<BaseModule> getChildList(List<BaseModule> list, BaseModule module) {
		List<BaseModule> li = new ArrayList<BaseModule>();
		Iterator<BaseModule> it = list.iterator();
		while (it.hasNext()) {
			BaseModule temp = (BaseModule) it.next();
			if (temp.getParentId() == module.getId()) {
				li.add(temp);
			}
		}
		return li;
	}

	public Tree getTreeJson() {
		if (this.list==null || this.list.size()==0) {
			return new Tree();
		}
		// 父菜单的id为0
		return this.getNodeJson(this.list, list.get(0));
	}

}
