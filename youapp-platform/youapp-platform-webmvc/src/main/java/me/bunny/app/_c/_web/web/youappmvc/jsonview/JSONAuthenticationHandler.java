package me.bunny.app._c._web.web.youappmvc.jsonview;

import me.bunny.app._c._web.WebCompProperties;
import me.bunny.app._c._web.access.subhub.AuthenticationAccessService;
import me.bunny.app._c._web.access.subhub.AuthenticationHookDelegateService;
import me.bunny.app._c._web.core.service.SessionUserImpl;
import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c._web.web.youappmvc.interceptor.AuthenticationHandler;
import me.bunny.app._c._web.web.youappmvc.interceptor.ServletExceptionUtil;
import me.bunny.app._c._web.web.youappmvc.support.APPFilterConfig;
import me.bunny.app._c.sps.support.memcached.subhub.MemcachedDelegateService;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

/**
 * 
 * the filter is only for Mobile , i.e. generally it's for the request sent from the <strong>Android or IOS</strong>.
 * for any other platforms such as Browser, from which the request never be intercepted. 
 * as so , you need be aware of the <strong>&lt;url-pattern></strong> , and
 * <strong>Note that some request based on servlet path need be forward. so you may configure servlet path ( {@link APPFilterConfig#SERVICE_ON_SERVLET_PATH} ),
 * <pre>
 * &lt;filter>
		&lt;filter-name>JMobileLoginFilter&lt;/filter-name>
		&lt;filter-class>j.jave.framework.components.web.mobile.JMobileLoginFilter&lt;/filter-class>
	&lt;/filter>
	&lt;filter-mapping>
		&lt;filter-name>JMobileLoginFilter&lt;/filter-name>
		&lt;servlet-name>JMobileServiceServlet&lt;/servlet-name>
		&lt;dispatcher>FORWARD&lt;/dispatcher>
		&lt;dispatcher>REQUEST&lt;/dispatcher>
	&lt;/filter-mapping>
 *</pre>
 * @author J
 * @see {@link APPFilterConfig}
 */
public class JSONAuthenticationHandler implements AuthenticationHandler {
	
	private AuthenticationAccessService authenticationAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	private AuthenticationHookDelegateService authenticationHookDelegateService=
			JServiceHubDelegate.get().getService(this, AuthenticationHookDelegateService.class);
	
	@Override
	public Object handleNoLogin(HttpContext httpContext) throws Exception {
		ResponseModel responseModel= ResponseModel.newNoLogin();
		return responseModel;
	}
	
	@Override
	public Object handleDuplicateLogin(HttpContext httpContext) throws Exception {
		ResponseModel responseModel= ResponseModel.newDuplicateLogin();
		responseModel.setData("already login.");
		return responseModel;
	}
	
	private MemcachedDelegateService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedDelegateService.class);;
	
	public static final String loginName=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_LOGIN_NAME, "_name");
	
	public static final String loginPassword=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_LOGIN_PASSWORD, "_password");
	
	private final int expiredTime=JConfiguration.get().getInt(WebCompProperties.YOUAPPMVC_TICKET_SESSION_EXPIRED_TIME, 1800);
	
	@Override
	public Object handleLogin(HttpContext httpContext) throws Exception {
		String name=httpContext.getParameter(loginName);
		String password=httpContext.getParameter(loginPassword);
		try{
			SessionUserImpl sessionUserImpl=authenticationAccessService.login(name, password);
			if(sessionUserImpl!=null){
				memcachedService.add(sessionUserImpl.getTicket(), expiredTime, sessionUserImpl);
				authenticationHookDelegateService.doAfterLogin(httpContext);
				ResponseModel responseModel= ResponseModel.newSuccessLogin();
				SessionUserImpl resSessionUserImpl=new SessionUserImpl();
				resSessionUserImpl.setTicket(sessionUserImpl.getTicket());
				resSessionUserImpl.setUserName(sessionUserImpl.getUserName());
				resSessionUserImpl.setUserId(sessionUserImpl.getUserId());
				responseModel.setData(sessionUserImpl);
				return responseModel;
			}
			else{
				ResponseModel responseModel= ResponseModel.newError();
				responseModel.setData("用户名或者密码错误");
				return responseModel;
			}
		}catch(Exception e){
			return ServletExceptionUtil.exception(httpContext, e);
		}
		
	}
	
	@Override
	public Object handleLoginout(HttpContext httpContext) throws Exception {
		try{
			memcachedService.remove(httpContext.getServiceContext().getTicket());
			authenticationHookDelegateService.doAfterLoginout(httpContext);
			ResponseModel responseModel= ResponseModel.newSuccess();
			responseModel.setData(true);
			return responseModel;
		}catch(Exception e){
			return ServletExceptionUtil.exception(httpContext, e);
		}
	}
	
	
	@Override
	public Object handleExpiredLogin(HttpContext httpContext) throws Exception {
		ResponseModel responseModel= ResponseModel.newExpiredLogin();
		responseModel.setData("ticket is expired.");
		return responseModel;
	}

}
