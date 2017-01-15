package me.bunny.app._c._web.web.youappmvc.interceptor;

import me.bunny.app._c._web.core.service.ServiceContext;
import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c._web.web.youappmvc.jsonview.JSONAuthenticationHandler;
import me.bunny.app._c._web.web.youappmvc.subhub.servletconfig.ServletConfigService;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class AuthenticationInterceptor implements ServletRequestInterceptor {
	
	protected static final JLogger LOGGER=JLoggerFactory.getLogger(AuthenticationInterceptor.class);
	
	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private AuthenticationHandler authenticationHandler=new JSONAuthenticationHandler();
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		HttpContext httpContext= servletRequestInvocation.getHttpContext();
		try{
			String path=httpContext.getVerMappingMeta().getMappingPath();
			ServiceContext serviceContext=httpContext.getServiceContext();
			if(servletConfigService.getLoginPath().equals(path)){ //login logical
				if(JStringUtils.isNullOrEmpty(serviceContext.getTicket())){
					return authenticationHandler.handleLogin(httpContext);
				}
				return authenticationHandler.handleDuplicateLogin(httpContext);
			}else if(servletConfigService.getLoginoutPath().equals(path)){ //loginout logical
				return authenticationHandler.handleLoginout(httpContext);
			}
			return servletRequestInvocation.proceed();
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			return e;
		}
	
	}
}
