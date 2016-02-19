package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedService;
import j.jave.platform.basicwebcomp.login.subhub.LoginAccessService;
import j.jave.platform.basicwebcomp.web.support.JFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.jsp.JJSPLoginFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.mobile.JMobileLoginFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.model.JHttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfigResolve;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.JYouAppMvcUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * super class contain common logic to intercept reqeust.
 * @author J
 * @see JJSPLoginFilter
 * @see JMobileLoginFilter
 */
public abstract class JLoginFilter implements JFilter ,APPFilterConfig {

	protected static final Logger LOGGER=LoggerFactory.getLogger(JLoginFilter.class);
	
	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private MemcachedService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedService.class);;
	
	private LoginAccessService loginAccessService= JServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	/**
	 * login path. such as "/login.loginaction/login"
	 */
	private String serviceLoginPath=servletConfigService.getLoginPath();
	
	/**
	 * i.e. "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String serviceServletPath=new APPFilterConfigResolve().resolveServiceOnServletPath(filterConfig);
		if(JStringUtils.isNotNullOrEmpty(serviceServletPath)){
			this.serviceServletPath=serviceServletPath;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String target=JYouAppMvcUtils.getPathInfo(req);
			if(!loginAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=JYouAppMvcUtils.getTicket(req);
			boolean isLogin=false;
			if(JStringUtils.isNullOrEmpty(clientTicket)){ // no login.
				isLogin=false;
			}
			else{ // check  whether server ticket is invalid. 
				JHttpContext serverTicket=(JHttpContext) memcachedService.get(clientTicket);
				if(serverTicket==null){
					isLogin=false;
				}
				else{
					isLogin=true;
				}
			}
			if(!isLogin){ // 没有登录， 跳转到登录页面
				 if(servletConfigService.getToLoginPath().equals(target)){
					 chain.doFilter(request, response);
					 return ;
				 }
				 else{
					 handleNoLogin(req, (HttpServletResponse) response, chain);
				 }
			}
			else{ // 已经登录
				if(serviceLoginPath.equals(target)){  // 不能重复登录
					handleDuplicateLogin(req, (HttpServletResponse) response, chain);
				}
				else if(servletConfigService.getToLoginPath().equals(target)){  // how to do with the request of going to login.
					handleToLogin(req, (HttpServletResponse) response, chain);
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
	
	/**
	 * how to do with no authorized access to a resource
	 * @param request
	 * @param response
	 * @param chain
	 */
	protected void handleNoLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception{
	}
	
	/**
	 * how to do with login more once.
	 * @param request
	 * @param response
	 * @param chain
	 */
	protected void handleDuplicateLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception{
	}
	
	/**
	 * how to do with to login request, the request is force the end-user go the login page.
	 * @param request
	 * @param response
	 * @param chain
	 */
	protected void handleToLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception{
	}

	@Override
	public void destroy() {
		
	}
	
	/**
	 * {@link #serviceServletPath} , if the 
	 * @return
	 */
	protected String getServiceServletPath(HttpServletRequest request) {
		return JStringUtils.isNullOrEmpty(serviceServletPath)?request.getServletPath():serviceServletPath;
	}

}
