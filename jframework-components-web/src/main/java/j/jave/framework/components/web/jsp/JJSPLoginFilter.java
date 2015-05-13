package j.jave.framework.components.web.jsp;

import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author J
 *
 */
public class JJSPLoginFilter implements Filter  {

	private static final Logger LOGGER=LoggerFactory.getLogger(JJSPLoginFilter.class);
	
	/**
	 * "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath="/default";
	
	/**
	 * default "/login.loginaction/login"
	 */
	private String serviceLoginPath="/login.loginaction/login";
	
	private String serviceToLoginPath="/login.loginaction/toLogin";
	
	private MemcachedService memcachedService= null;
	
	private LoginAccessService loginAccessService= JServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String tempPath=filterConfig.getInitParameter("serviceServletPath");
		if(tempPath!=null){
			tempPath=tempPath.trim();
			if(tempPath.endsWith("/*")){
				this.serviceServletPath=tempPath.substring(0, tempPath.length()-2);
			}
			else if(tempPath.endsWith("/")){
				this.serviceServletPath=tempPath.substring(0, tempPath.length()-1);
			}
			else {
				this.serviceServletPath=tempPath;
			}
		}
		
		tempPath=filterConfig.getInitParameter("serviceLoginPath");
		memcachedService=JServiceHubDelegate.get().getService(this,MemcachedService.class);
		loginAccessService=JServiceHubDelegate.get().getService(this,LoginAccessService.class);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			String target=HTTPUtils.getPathInfo(req);
			
			if(!loginAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=HTTPUtils.getTicket(req);
			boolean isLogin=false;
			if(JStringUtils.isNullOrEmpty(clientTicket)){ // no login.
				isLogin=false;
			}
			else{ // check  whether server ticket is invalid. 
				HTTPContext serverTicket=(HTTPContext) memcachedService.get(clientTicket);
				if(serverTicket==null){
					isLogin=false;
				}
				else{
					isLogin=true;
				}
			}
			if(!isLogin){ // 没有登录， 跳转到登录页面
				req.getRequestDispatcher(this.serviceServletPath+serviceToLoginPath).forward(req, response);
			}
			else{ // 已经登录
				if(serviceLoginPath.equals(target)){  // 不能重复登录
					req.getRequestDispatcher(HTTPUtils.getAppUrlPath(req)).forward(req, response);
				}
				else{
					chain.doFilter(request, response);
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {
		
	}

}
