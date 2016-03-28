package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedWithSpringConfigService;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.basicwebcomp.web.support.JFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.JSONAuthenticationHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPLoginHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * super class contain common logic to intercept reqeust.
 * @author J
 * @see JSPLoginHandler
 * @see JSONAuthenticationHandler
 */
public class AuthenticationFilter implements JFilter ,APPFilterConfig {

	protected static final JLogger LOGGER=JLoggerFactory.getLogger(AuthenticationFilter.class);
	
	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private MemcachedWithSpringConfigService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedWithSpringConfigService.class);;
	
	private AuthenticationAccessService authenticationAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	private AuthenticationHandler authenticationHandler=new JSONAuthenticationHandler();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String target=YouAppMvcUtils.getPathInfo(req);
			if(!authenticationAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=YouAppMvcUtils.getTicket(req);
			boolean isLogin=false;
			if(JStringUtils.isNullOrEmpty(clientTicket)){ // no login.
				isLogin=false;
			}
			else{ // check  whether server ticket is invalid. 
				HttpContext serverTicket=(HttpContext) memcachedService.get(clientTicket);
				if(serverTicket==null){
					isLogin=false;
				}
				else{
					isLogin=true;
				}
			}
			if(!isLogin){ // 没有登录， 尝试登陆
				 if(servletConfigService.getLoginPath().equals(target)){ // 尝试登陆
					 authenticationHandler.handleLogin(req, (HttpServletResponse)response, chain);
					 return ;
				 }
				 else{
					 authenticationHandler.handleNoLogin(req, (HttpServletResponse) response, chain);
					 return;
				 }
			}
			else{ // 已经登录
				if(servletConfigService.getLoginPath().equals(target)){  // 不能重复登录
					authenticationHandler.handleDuplicateLogin(req, (HttpServletResponse) response, chain);
					return;
				}
//				else if(servletConfigService.getToLoginPath().equals(target)){  // how to do with the request of going to login.
//					loginHandler.handleToLogin(req, (HttpServletResponse) response, chain);
//					return;
//				}
				else{
					chain.doFilter(request, response);
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			FilterExceptionUtil.exception(request, response, e);
		}
	}

	@Override
	public void destroy() {
		
	}

}
