package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateService;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.JSONAuthenticationHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor implements ServletRequestInterceptor {
	
protected static final JLogger LOGGER=JLoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private MemcachedDelegateService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedDelegateService.class);;
	
	private AuthenticationAccessService authenticationAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	private AuthenticationHandler authenticationHandler=new JSONAuthenticationHandler();
	
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		HttpServletRequest req= servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse response=servletRequestInvocation.getHttpServletResponse();
		try{
			// REMOVE LOGIN INFO FROM LOCAL.
			HttpContextHolder.remove();
			String clientTicket=YouAppMvcUtils.getTicket(req);
			HttpContext serverTicket=null;
			if(JStringUtils.isNotNullOrEmpty(clientTicket)){ // already login
				serverTicket=(HttpContext) memcachedService.get(clientTicket);
				if(serverTicket==null){
					return authenticationHandler.handleExpiredLogin(req, (HttpServletResponse) response);
				}
			}
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String target=servletRequestInvocation.getMappingPath();
			if(!servletConfigService.getLoginPath().equals(target)
					&&!servletConfigService.getLoginoutPath().equals(target)
					&&!authenticationAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限, 仍然尝试获取登录用户信息
				if(serverTicket==null){
					// no login, mock a login user.
					HttpContext httpContext=HttpContextHolder.getMockHttpContext();
					httpContext.initHttpClient(req, (HttpServletResponse) response);
					HttpContextHolder.set(httpContext);
				}
				else{
					serverTicket.initHttpClient(req, (HttpServletResponse) response);
					HttpContextHolder.set(serverTicket);
				}
				return servletRequestInvocation.proceed();
			}
			
			boolean isLogin=false;
			if(serverTicket==null){
				isLogin=false;
			}
			else{
				isLogin=true;
			}
			if(!isLogin){ // 没有登录， 尝试登陆
				 if(servletConfigService.getLoginPath().equals(target)){ // 尝试登陆
					 return authenticationHandler.handleLogin(req, (HttpServletResponse)response);
				 }
				 else{
					 return authenticationHandler.handleNoLogin(req, (HttpServletResponse) response);
				 }
			}
			else{ // 已经登录
				if(servletConfigService.getLoginPath().equals(target)){  // 不能重复登录
					return authenticationHandler.handleDuplicateLogin(req, (HttpServletResponse) response);
				}
				if(servletConfigService.getLoginoutPath().equals(target)){  // 登出
					return authenticationHandler.handleLoginout(req, (HttpServletResponse) response,serverTicket);
				}
//				else if(servletConfigService.getToLoginPath().equals(target)){  // how to do with the request of going to login.
//					loginHandler.handleToLogin(req, (HttpServletResponse) response, chain);
//					return;
//				}
				else{
					serverTicket.initHttpClient(req, (HttpServletResponse) response);
					HttpContextHolder.set(serverTicket);
					return servletRequestInvocation.proceed();
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(req, response, e);
		}
	
	}
}
