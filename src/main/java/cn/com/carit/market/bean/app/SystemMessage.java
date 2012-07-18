package cn.com.carit.market.bean.app;

import java.io.Serializable;
import java.util.Date;

import org.codehaus.jackson.annotate.JsonIgnore;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import cn.com.carit.market.bean.BaseBean;
import cn.com.carit.market.common.jackjson.CustomDateSerializer;

/**
 * 系统消息
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-7-13
 */
public class SystemMessage extends BaseBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2110735046819560121L;
	
	public static final int CATALOG_SYSTEM_PUSH=0;
	public static final int CATALOG_APP_UPDATED=1;
	
	public static final int STATUS_UNREAD=0;
	public static final int STATUS_READ=1;
	/**账号Id*/
	private Integer accountId;
	/**消息类别*/
	private Integer catalog;
	/**标题*/
	private String title;
	/**内容*/
	private String content;
	/**状态*/
	private Integer status;
	/**账号ID列表*/
	@JsonIgnore
	private String [] accountIds;
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Integer getCatalog() {
		return catalog;
	}
	public void setCatalog(Integer catalog) {
		this.catalog = catalog;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	
	public String[] getAccountIds() {
		return accountIds;
	}
	public void setAccountIds(String[] accountIds) {
		this.accountIds = accountIds;
	}
	
	
	@Override
	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getUpdateTime() {
		return super.getUpdateTime();
	}
	@Override
	public String toString() {
		return "SystemMessage [accountId=" + accountId + ", catalog=" + catalog
				+ ", title=" + title + ", content=" + content + ", status="
				+ status + "]";
	}
	
}
