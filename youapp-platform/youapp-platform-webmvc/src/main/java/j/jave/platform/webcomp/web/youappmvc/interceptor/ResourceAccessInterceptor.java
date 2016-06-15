package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.webcomp.web.youappmvc.utils.YouAppMvcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * filter on all request , check if the request is authorized on the end-user.
 * the filter may follow from JJSPLoginFilter, but the filter also intercept those request from mobile platform such as Android, IOS
 * <p> Common resource such as  Javascript , CSS or Image etc never be intercepted. 
 * So recommend configure the filter mapping using sample below:
 * <pre>
 * &lt;filter-mapping>
		&lt;filter-name>ResourceAccessFilter&lt;/filter-name>
		&lt;servlet-name>JJSPServiceServlet&lt;/servlet-name>
		&lt;servlet-name>JMobileServiceServlet&lt;/servlet-name>
		&lt;dispatcher>FORWARD&lt;/dispatcher>
		&lt;dispatcher>REQUEST&lt;/dispatcher>
		&lt;dispatcher>INCLUDE&lt;/dispatcher>
	&lt;/filter-mapping>    
 * @author J
 */
public class ResourceAccessInterceptor implements ServletRequestInterceptor{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ResourceAccessInterceptor.class);
	
	private AuthenticationAccessService loginAccessService=JServiceHubDelegate.get().getService(this,AuthenticationAccessService.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {

		HttpServletRequest req=(HttpServletRequest) servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse response=servletRequestInvocation.getHttpServletResponse();
		
		try{
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String pathInfo=servletRequestInvocation.getMappingPath();
			 
			if(!loginAccessService.isNeedLoginRole(pathInfo)){
				// 资源不需要登录权限
				return servletRequestInvocation.proceed();
			}
			
			String clientTicket=YouAppMvcUtils.getTicket(req);
			
			// IF LOGINED, need check whether has an access to the resource
			if(JStringUtils.isNotNullOrEmpty(clientTicket)){
				HttpContext context=HttpContextHolder.get();
				if(context!=null){
					boolean authorized=loginAccessService.authorizeOnUserId(pathInfo, context.getUser().getUserId());
					authorized=true;
					if(!authorized){
						ResponseModel responseModel=ResponseModel.newNoAccess();
						responseModel.setData("have no access to the resource.");
//						HttpServletResponseUtil.write(req, (HttpServletResponse) response, HttpContextHolder.get(), responseModel);
						return responseModel;
					}
				}
				else{
					ResponseModel responseModel=ResponseModel.newNoLogin();
					responseModel.setData("login user information [ticket:"+clientTicket+"] miss, refresh your broswer to re-login");
					
					YouAppMvcUtils.removeTicket(req, (HttpServletResponse) response);
//					HttpServletResponseUtil.write(req, (HttpServletResponse) response, HttpContextHolder.get(), responseModel);
					return responseModel;
				}
			}
			return servletRequestInvocation.proceed();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(req, response, e);
		}
	}
	
	
}
