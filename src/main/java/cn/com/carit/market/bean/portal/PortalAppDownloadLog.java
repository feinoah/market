package cn.com.carit.market.bean.portal;

import org.codehaus.jackson.annotate.JsonIgnore;

public class PortalAppDownloadLog {
	@JsonIgnore
	private String local;
	private int accountId;
	private Integer appId;
	private String appName;
	
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
	public Integer getAppId() {
		return appId;
	}
	public void setAppId(Integer appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
}
