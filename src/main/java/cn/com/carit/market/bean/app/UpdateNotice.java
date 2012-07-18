package cn.com.carit.market.bean.app;
/**
 * 应用更新消息Bean
 * @author <a href="mailto:xiegengcai@gmail.com">Xie Gengcai</a>
 * 2012-7-13
 */
public class UpdateNotice {
	private int appId;
	private String appName;
	private String appEnName;
	private String appVesion;
	private int accountId;
	public int getAppId() {
		return appId;
	}
	public void setAppId(int appId) {
		this.appId = appId;
	}
	public String getAppName() {
		return appName;
	}
	public void setAppName(String appName) {
		this.appName = appName;
	}
	public String getAppEnName() {
		return appEnName;
	}
	public void setAppEnName(String appEnName) {
		this.appEnName = appEnName;
	}
	public String getAppVesion() {
		return appVesion;
	}
	public void setAppVesion(String appVesion) {
		this.appVesion = appVesion;
	}
	public int getAccountId() {
		return accountId;
	}
	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}
}
