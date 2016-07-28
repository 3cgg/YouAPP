package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.support.JServletContext;
import j.jave.platform.webcomp.web.youappmvc.servlet.MvcServiceServlet;
import j.jave.platform.webcomp.web.youappmvc.support.APPFilterConfig;


/**
 * Do with URL with slash appended （like : http://127.0.0.1:8686/youapp/） or any other cases, check if the path is valid , 
 * what that means the path can be processed by {@link MvcServiceServlet}. if not valid ,  an entrance view page (<strong>only for browser</strong>) will be shown ( in the case of requesting root resource) ,
 * or return error message JSON format that is from FilterResponse. 
 * <p>Also note : some dispatch may need put concrete servlet path ( configured by {@link APPFilterConfig#SERVICE_ON_SERVLET_PATH} ) as prefix of action path.see  APPFilterConfig for detail.
 * if the parameter is not configured, will call {@link JServletContext#getJSPServletUrlMappingResolvingStar()} to get default servlet path.
 * <p>A sample below shown :
 * <pre>
 * &lt;filter>
 *		&lt;filter-name>JValidPathFilter&lt;/filter-name>
 *		&lt;filter-class>j.jave.framework.components.web.multi.platform.filter.JValidPathFilter&lt;/filter-class>
 *		&lt;init-param>
 *			&lt;param-name>serviceServletPath&lt;/param-name>
 *			&lt;param-value>/web/service/dispatch/*&lt;/param-value>
 *		&lt;/init-param>
 *	&lt;/filter>
 *	&lt;filter-mapping>
 *		&lt;filter-name>JValidPathFilter&lt;/filter-name>
 *		&lt;url-pattern>/*&lt;/url-pattern>
 *	&lt;/filter-mapping>
 * </pre>
 * @author J
 * @see JServletContext
 */
public class ValidPathInterceptor implements ServletRequestInterceptor  {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ValidPathInterceptor.class);
	
	private AuthenticationAccessService loginAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		
		try{
			String path=servletRequestInvocation.getHttpContext().getVerMappingMeta().getMappingPath();
			if(JStringUtils.isNotNullOrEmpty(path)&&!"/".equals(path)){
				boolean validPath=loginAccessService.isValidResource(path);
				if(!validPath){
					ResponseModel responseModel=ResponseModel.newInvalidPath();
					responseModel.setData("the url is missing, url : "+path);
					return responseModel;
				}
				return servletRequestInvocation.proceed();
			}
			else{
				ResponseModel responseModel=ResponseModel.newError().setData("the root url is not supported.");
				return responseModel;
			}
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			return e;
		}
	}

}
