package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.access.subhub.AuthenticationAccessService;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.support.JServletContext;
import j.jave.platform.basicwebcomp.web.support.JServletDetect;
import j.jave.platform.basicwebcomp.web.youappmvc.servlet.MvcServiceServlet;
import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


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
 * @see APPFilterConfig
 * @see JServletContext
 * @see FilterResponse
 */
public class ValidPathInterceptor implements ServletRequestInterceptor  {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ValidPathInterceptor.class);
	
	private ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private AuthenticationAccessService loginAccessService= JServiceHubDelegate.get().getService(this, AuthenticationAccessService.class);
	
	private JServletDetect servletDetect=null;
	
	private JServletContext servletContext=null;
	
	/**
	 * "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath=null;
	
	private Object sync=new Object();
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		HttpServletRequest request= servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse response=servletRequestInvocation.getHttpServletResponse();
		
		try{
			
			if(servletDetect==null){
				synchronized (sync) {
					if(servletDetect==null){
						servletDetect=new JServletDetect(request);
						servletContext=servletDetect.getServletContext();
					}
				}
			}
			
			if(servletContext!=null){
				String servletPath=request.getServletPath();
				
				//forward to login view page, if request root resource, note it's extend to browser
				if(JStringUtils.isNullOrEmpty(servletPath)||"/".equals(servletPath)){
					String entranceViewPath="";
					if(JStringUtils.isNotNullOrEmpty(serviceServletPath)){
						entranceViewPath=serviceServletPath+servletConfigService.getToLoginPath();
					}
					else {
						entranceViewPath=servletContext.getJSPServletUrlMappingResolvingStar()+servletConfigService.getToLoginPath();
					}
					request.getRequestDispatcher(entranceViewPath).forward(request, response);
					return null;
				}
				
				servletPath=servletPath+"/";
				boolean isServletPath=servletContext.containServletPath(servletPath);
				if(!isServletPath){
					return servletRequestInvocation.proceed();
				}
			}
			
			String path=YouAppMvcUtils.getPathInfo(request);
			if(JStringUtils.isNotNullOrEmpty(path)){
				boolean validPath=loginAccessService.isValidResource(path);
				if(!validPath){
					ResponseModel responseModel=ResponseModel.newInvalidPath();
					responseModel.setData(servletConfigService.getInvalidPathInfo());
//					HttpServletResponseUtil.write(request, (HttpServletResponse) response, HttpContextHolder.get(), responseModel);
					return responseModel;
				}
			}
			return servletRequestInvocation.proceed();
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			return ServletExceptionUtil.exception(request, response, e);
		}
	}

}