package cn.com.carit.market.web.interseptor;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.com.carit.market.bean.BaseModule;
import cn.com.carit.market.bean.BaseUser;
import cn.com.carit.market.common.Constants;
import cn.com.carit.market.common.utils.AttachmentUtil;

/**
 * 后台系统拦截器
 * @author <a href="mailto:xiegengcai@gmail.com">Ivan Xie</a>
 *
 */
public class AdminInterceptor extends HandlerInterceptorAdapter{
	private final Logger log = Logger.getLogger(getClass());
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String uri = request.getRequestURI();
		log.debug("Request for: "+uri);
		String hostPath="http://"+request.getLocalName();
		String contexPath="/market";
		if (uri.indexOf(contexPath)!=-1) { // 开发环境
			uri=uri.replaceFirst(contexPath, "");
			hostPath+=":"+request.getLocalPort()+contexPath;
		} 
		// 初始化附件配置
		AttachmentUtil.init(hostPath);
		log.debug("Request for: "+uri);
		if (uri.indexOf("admin")!=-1) { // 管理员相关URL
			BaseUser user=(BaseUser) request.getSession().getAttribute(
					Constants.ADMIN_USER);
			if (user==null) { // 没有登录
				log.info("not login...");
				//转到登录页面
				request.getRequestDispatcher("/back/loginForm").forward(request, response);
				return false;
			} else {
				return authorized(uri, request, response);
			}
		}
		return super.preHandle(request, response, handler);
	}

	/**
	 * 检查是否授权
	 * @param uri
	 * @param req
	 * @return
	 * @throws IOException 
	 * @throws ServletException 
	 */
	@SuppressWarnings("unchecked")
	private boolean authorized(String uri, HttpServletRequest req, HttpServletResponse resp) 
			throws ServletException, IOException{
		List<BaseModule> allModule=(List<BaseModule>) req.getSession().getAttribute(
				Constants.USER_ALL_MOUDLE);
		for (BaseModule module : allModule) {
			if (uri.equals(module.getModuleUrl())) { // 有授权
				return true;
			}
		}
		log.error("Unauthorized Request for: "+uri);
		//转到登录页面
		req.getRequestDispatcher("/back/unauthorized").forward(req, resp);
		return false;
	}
}
