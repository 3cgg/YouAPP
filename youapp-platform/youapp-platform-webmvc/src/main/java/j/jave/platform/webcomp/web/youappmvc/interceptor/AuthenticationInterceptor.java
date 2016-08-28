package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.jsonview.JSONAuthenticationHandler;
import j.jave.platform.webcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;

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
