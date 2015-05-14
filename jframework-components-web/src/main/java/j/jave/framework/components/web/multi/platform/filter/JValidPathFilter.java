package j.jave.framework.components.web.multi.platform.filter;

import j.jave.framework.components.web.servlet.JServiceServlet;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.subhub.servlet.config.ServletConfigService;
import j.jave.framework.components.web.support.JFilter;
import j.jave.framework.components.web.support.JServletContext;
import j.jave.framework.components.web.support.JServletDetect;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;


/**
 * Do with URL with slash appended （like : http://127.0.0.1:8686/youapp/） or any other cases, check if the path is valid , 
 * what that means the path can be processed by {@link JServiceServlet}. if not valid ,  an entrance view page will be shown,
 * or return error message JSON format.
 * <p>Also note : some dispatch may need put concrete servlet path ( configured by {@link APPFilterConfig#SERVICE_ON_SERVLET_PATH} ) as prefix of action path.see  APPFilterConfig for detail.
 * if the parameter is not configured, will call {@link JServletContext#getJSPServletUrlMappingResolvingStar()} to get default servlet path.
 * <p>A sample below shown :
 * <pre>
 * &lt;filter>
		&lt;filter-name>JValidPathFilter&lt;/filter-name>
		&lt;filter-class>j.jave.framework.components.web.multi.platform.filter.JValidPathFilter&lt;/filter-class>
	&lt;/filter>
	&lt;filter-mapping>
		&lt;filter-name>JValidPathFilter&lt;/filter-name>
		&lt;url-pattern>/*&lt;/url-pattern>
	&lt;/filter-mapping>
 * </pre>
 * @author J
 * @see APPFilterConfig
 * @see JServletContext
 */
public class JValidPathFilter implements JFilter ,APPFilterConfig  {
	
	private ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private LoginAccessService loginAccessService= JServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	private JServletDetect servletDetect=null;
	
	private JServletContext servletContext=null;;
	
	/**
	 * "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath=null;
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		String serviceServletPath=new APPFilterConfigResolve().resolveServiceOnServletPath(filterConfig);
		if(JStringUtils.isNotNullOrEmpty(serviceServletPath)){
			this.serviceServletPath=serviceServletPath;
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req=(HttpServletRequest) request;
		if(servletDetect==null){
			servletDetect=new JServletDetect(req);
			servletContext=servletDetect.getServletContext();
		}
		
		if(servletContext!=null){
			String servletPath=req.getServletPath();
			
			//forward to entrance view page, if request root resource.
			if(JStringUtils.isNullOrEmpty(servletPath)||"/".equals(servletPath)){
				String entranceViewPath="";
				if(JStringUtils.isNotNullOrEmpty(serviceServletPath)){
					entranceViewPath=serviceServletPath+servletConfigService.getEntranceViewPath();
				}
				else {
					entranceViewPath=servletContext.getJSPServletUrlMappingResolvingStar()+servletConfigService.getEntranceViewPath();
				}
				req.getRequestDispatcher(entranceViewPath).forward(request, response);
				return ;
			}
			
			servletPath=servletPath+"/";
			boolean isServletPath=servletContext.containServletPath(servletPath);
			if(!isServletPath){
				chain.doFilter(request, response);
				return ;
			}
		}
		
		String path=HTTPUtils.getPathInfo(req);
		if(JStringUtils.isNotNullOrEmpty(path)){
			boolean validPath=loginAccessService.isValidResource(path);
			if(!validPath){
				response.getOutputStream().write(servletConfigService.getInvalidPathInfo().getBytes("utf-8"));
				return ;
			}
		}
		chain.doFilter(request, response); 
	}

	@Override
	public void destroy() {
		
	}

}
