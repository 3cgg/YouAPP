package j.jave.framework.components.views;

import j.jave.framework.components.core.hub.ServiceHub;
import j.jave.framework.components.core.hub.ServiceHub.StandardServiceInterfaces;
import j.jave.framework.components.login.service.LoginAccessService;
import j.jave.framework.components.memcached.JMemcachedDistService;
import j.jave.framework.utils.JUtils;

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


public class JLoginFilter implements Filter  {

	private static final Logger LOGGER=LoggerFactory.getLogger(JLoginFilter.class);
	
	/**
	 * "/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath="/default";
	
	/**
	 * default "/login.loginaction/login"
	 */
	private String serviceLoginPath="/login.loginaction/login";
	
	private String serviceToLoginPath="/login.loginaction/toLogin";
	
	private static JMemcachedDistService jMemcachedDist= null;
	
	private static LoginAccessService loginAccessService= null;
	
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
		jMemcachedDist=(JMemcachedDistService)ServiceHub.get().getService(StandardServiceInterfaces.MEMCACHED_DIST_SERVICE);
		loginAccessService=(LoginAccessService)ServiceHub.get().getService(StandardServiceInterfaces.LOGIN_ACCESS_SERVICE);
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			String contextPath=req.getContextPath()+this.serviceServletPath;
			String target=req.getRequestURI().substring(contextPath.length());
			
			if(!loginAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=CookieUtils.getValue(req, "ticket");
			boolean isLogin=false;
			if(JUtils.isNullOrEmpty(clientTicket)){ // no login.
				isLogin=false;
			}
			else{ // check  whether server ticket is invalid. 
				HTTPContext serverTicket=(HTTPContext) jMemcachedDist.get(clientTicket);
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
