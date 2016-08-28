package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
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
	public Object intercept(ServletRequestInvocation servletRequestInvocation) throws Throwable {
		
		try{
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String pathInfo=servletRequestInvocation.getHttpContext().getVerMappingMeta().getMappingPath();
			 
			if(!loginAccessService.isNeedAuthorize(pathInfo)){
				// 资源不需要授权
				return servletRequestInvocation.proceed();
			}
			HttpContext context=servletRequestInvocation.getHttpContext();
			if(JStringUtils.isNotNullOrEmpty(context.getServiceContext().getTicket())){
				String userId=context.getServiceContext().getUserId();
				boolean authorized=loginAccessService.authorizeOnUserId(pathInfo, userId);
				authorized=true;
				if(!authorized){
					ResponseModel responseModel=ResponseModel.newNoAccess();
					responseModel.setData("have no access to the resource.");
					return responseModel;
				}
			}
			else{
				ResponseModel responseModel=ResponseModel.newNoLogin();
				responseModel.setData("have no access to the resource.");
				return responseModel;
			}
			return servletRequestInvocation.proceed();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			throw e;
		}
	}
	
	
}
