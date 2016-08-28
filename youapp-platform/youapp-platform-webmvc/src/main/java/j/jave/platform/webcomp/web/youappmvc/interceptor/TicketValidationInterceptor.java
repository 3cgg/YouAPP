package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.sps.support.memcached.subhub.MemcachedDelegateService;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.SessionUser;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.jsonview.JSONAuthenticationHandler;

public class TicketValidationInterceptor implements ServletRequestInterceptor {
	
	protected static final JLogger LOGGER=JLoggerFactory.getLogger(TicketValidationInterceptor.class);
	
	private MemcachedDelegateService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedDelegateService.class);;
	
	private AuthenticationHandler authenticationHandler=new JSONAuthenticationHandler();
	
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		try{
			HttpContext httpContext= servletRequestInvocation.getHttpContext();
			ServiceContext serviceContext=httpContext.getServiceContext();
			String ticket=serviceContext.getTicket();
			if(JStringUtils.isNotNullOrEmpty(ticket)){ // already login
				SessionUser sessionUser=(SessionUser) memcachedService.get(ticket);
				if(sessionUser==null){
					return authenticationHandler.handleExpiredLogin(httpContext);
				}
				httpContext.setUser(sessionUser);
				httpContext.getServiceContext(true);
			}
			return servletRequestInvocation.proceed();
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			return e;
		}
	
	}
}
