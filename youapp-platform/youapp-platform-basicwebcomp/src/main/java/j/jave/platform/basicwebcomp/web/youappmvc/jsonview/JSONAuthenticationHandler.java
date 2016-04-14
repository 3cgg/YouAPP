package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateService;
import j.jave.platform.basicwebcomp.WebCompProperties;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationHookDelegateService;
import j.jave.platform.basicwebcomp.core.service.SessionUserImpl;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.util.JCookieUtils;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.ViewConstants;
import j.jave.platform.basicwebcomp.web.youappmvc.interceptor.AuthenticationHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.interceptor.ServletExceptionUtil;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class JSONAuthenticationHandler implements AuthenticationHandler ,APPFilterConfig {
	
	private AuthenticationAccessService authenticationAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	private AuthenticationHookDelegateService authenticationHookDelegateService=
			JServiceHubDelegate.get().getService(this, AuthenticationHookDelegateService.class);
	
	@Override
	public Object handleNoLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseModel responseModel= ResponseModel.newNoLogin();
		return responseModel;
	}
	
	@Override
	public Object handleDuplicateLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		ResponseModel responseModel= ResponseModel.newDuplicateLogin();
		return responseModel;
	}

	@Override
	public void handleToLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
	}
	
	private MemcachedDelegateService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedDelegateService.class);;
	
	private static final String loginName=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_LOGIN_NAME, "_name");
	
	private static final String loginPassword=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_LOGIN_PASSWORD, "_password");
	
	private final int expiredTime=JConfiguration.get().getInt(WebCompProperties.YOUAPPMVC_TICKET_SESSION_EXPIRED_TIME, 1800);
	
	@Override
	public Object handleLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String name=request.getParameter(loginName);
		String password=request.getParameter(loginPassword);
		HttpContext httpContext=null;
		try{
			SessionUserImpl sessionUserImpl=authenticationAccessService.login(name, password);
			if(sessionUserImpl!=null){
				httpContext=new HttpContext();
				httpContext.setTicket(sessionUserImpl.getTicket());
				httpContext.setUser(sessionUserImpl);
				memcachedService.add(sessionUserImpl.getTicket(), expiredTime, httpContext);
				httpContext.initHttpClient(request, response);
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
			return ServletExceptionUtil.exception(request, response, e);
		}
		
	}
	
	@Override
	public Object handleLoginout(HttpServletRequest request,
			HttpServletResponse response,HttpContext httpContext) throws Exception {
		try{
			memcachedService.remove(httpContext.getTicket());
			authenticationHookDelegateService.doAfterLoginout(httpContext);
			ResponseModel responseModel= ResponseModel.newSuccess();
			responseModel.setData(true);
			return responseModel;
		}catch(Exception e){
			return ServletExceptionUtil.exception(request, response, e);
		}
	}
	
	
	@Override
	public Object handleExpiredLogin(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		JCookieUtils.deleteCookie(request, response, JCookieUtils.getCookie(request, ViewConstants.TICKET));
		ResponseModel responseModel= ResponseModel.newExpiredLogin();
		return responseModel;
	}

}
