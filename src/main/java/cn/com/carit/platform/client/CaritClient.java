package cn.com.carit.platform.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;

import cn.com.carit.market.common.utils.JsonUtil;
import cn.com.carit.platform.response.LogonResponse;


/**
 * <p>
 * <b>功能说明：</b>
 * </p>
 * @author <a href="mailto:xiegengcai@gmail.com">Gengcai Xie</a>
 * @date 2012-9-24
 */
public class CaritClient {
	@Resource
	private CookieLocaleResolver localeResolver;
	private static final Logger logger=LoggerFactory.getLogger(CaritClient.class);
	
	public enum MessageFormat{
		xml, json;
	}
	
	public enum Local{
		zh_CN, en;
	}
	
	public static final String SYSTEM_PARAM_APPKEY="appKey";
	public static final String SYSTEM_PARAM_MESSAGE_FORMAT="messageFormat";
	public static final String SYSTEM_PARAM_METHOD="method";
	public static final String SYSTEM_PARAM_VERSION="v";
	public static final String SYSTEM_PARAM_LOCALE="locale";
	public static final String SYSTEM_PARAM_SIGN="sign";
	public static final String SYSTEM_PARAM_SESSION_ID="sessionId";
	
	public static final String HTTP_METHOD_GET="GET";
	public static final String HTTP_METHOD_POST="POST";
	
	public static final String CHARSET_ENCODING_UTF_8="UTF-8";
	
	//服务地址
    private String serverUrl;

    //应用键
    private String appKey;

    //应用密钥
    private String appSecret;

    private String sessionId;
    
    private String locale = Locale.SIMPLIFIED_CHINESE.toString();
    
    private String messageFormat=MessageFormat.json.toString();
    
    private Timer timer;
    

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getServerUrl() {
		return serverUrl;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}
	
    public String getMessageFormat() {
		return messageFormat;
	}

	public void setMessageFormat(String messageFormat) {
		this.messageFormat = messageFormat;
	}

	public Map<String, String> buildParamValues(String method, String version) {
		Map<String, String> paramValues=new HashMap<String, String>();
		paramValues.put(SYSTEM_PARAM_METHOD, method);
		paramValues.put(SYSTEM_PARAM_APPKEY, appKey);
		paramValues.put(SYSTEM_PARAM_VERSION, version);
		paramValues.put(SYSTEM_PARAM_LOCALE, locale);
		paramValues.put(SYSTEM_PARAM_MESSAGE_FORMAT, CaritClient.MessageFormat.json.toString());
		return paramValues;
	}
	
	public Map<String, String> buildParamValues(String method, String version, String sessionId) {
		Map<String, String> paramValues=new HashMap<String, String>();
		paramValues.put(SYSTEM_PARAM_METHOD, method);
		paramValues.put(SYSTEM_PARAM_APPKEY, appKey);
		paramValues.put(SYSTEM_PARAM_VERSION, version);
		paramValues.put(SYSTEM_PARAM_LOCALE, locale);
		paramValues.put(SYSTEM_PARAM_MESSAGE_FORMAT, CaritClient.MessageFormat.json.toString());
		paramValues.put(SYSTEM_PARAM_SESSION_ID, sessionId);
		return paramValues;
	}
	
	/**
	 * 获取session
	 * @throws Exception
	 */
	public void getSession() throws Exception{
		if(this.getSessionId()==null){
			Map<String, String> paramValues=this.buildParamValues("platform.getSession", "1.0");
			// 生成签名
			String sign=ClientUtils.sign(paramValues, this.appSecret);
			// 不需要签名的参数放后面
			paramValues.put(SYSTEM_PARAM_SIGN, sign);
			// 获取响应
			String resonse=getHttpResponse(ClientUtils.buildRequestUrl(this.serverUrl, paramValues), "POST");
			try{
				LogonResponse response=(LogonResponse) JsonUtil.jsonToObject(resonse, LogonResponse.class);
				// 给客户端实例设置sessionId
				this.setSessionId(response.getSessionId());
				// 开启一个延时5分钟每间隔5分钟执行一次的定时器模拟长链接
				timer=new Timer("keep connection timer", false);
				timer.schedule(new TimerTask() {
					
					@Override
					public void run() {
						Map<String, String> paramValues=buildParamValues("platform.heartbeat", "1.0", getSessionId());
						// 生成签名
						String sign=ClientUtils.sign(paramValues, appSecret);
						// 不需要签名的参数放后面
						paramValues.put(SYSTEM_PARAM_SIGN, sign);
						// 获取响应
						getHttpResponse(paramValues);
					}
					
				}, 5*60*1000, 5*60*1000);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
		
	}
	
	/**
	 * 删除session
	 * @throws Exception
	 */
	public void removeSession(){
		if(this.getSessionId()!=null){
			Map<String, String> paramValues=this.buildParamValues("platform.removeSession", "1.0", this.getSessionId());
			// 生成签名
			String sign=ClientUtils.sign(paramValues, appSecret);
			// 不需要签名的参数放后面
			paramValues.put(SYSTEM_PARAM_SIGN, sign);
			// 获取响应
			postHttpResponse(paramValues);
		}
	}
	
	/**
	 * 
	 * @param url
	 * @param method
	 * @return
	 */
	public String getHttpResponse(String url, String method) {
		String response = null;
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		try {
			conn = (HttpURLConnection) new URL(url).openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Charset", CHARSET_ENCODING_UTF_8);
			int responseCode = conn.getResponseCode();
			if (responseCode == HttpURLConnection.HTTP_OK) {
				reader = new BufferedReader(new InputStreamReader(
						conn.getInputStream(), CHARSET_ENCODING_UTF_8));
				// 防止多行响应数据，变更读取方法
				char [] cbuf=new char[2048];
				StringBuilder sb=new StringBuilder();
				int n=reader.read(cbuf);
				while (n!=-1) {
					sb.append(cbuf);
					n=reader.read(cbuf);
				}
//				response = reader.readLine();
				response=sb.toString();
			} else {
				response = "{\"httpError\":" + responseCode + "}";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}
		return response.trim();
	}

	public String getHttpResponse(Map<String, String> paramValues){
		return getHttpResponse(ClientUtils.buildRequestUrl(serverUrl, paramValues), HTTP_METHOD_GET);
	}
	
	public String postHttpResponse(Map<String, String> paramValues){
		return getHttpResponse(ClientUtils.buildRequestUrl(serverUrl, paramValues), HTTP_METHOD_POST);
	}
	
}
