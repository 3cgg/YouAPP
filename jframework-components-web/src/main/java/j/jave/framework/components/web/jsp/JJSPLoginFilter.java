package j.jave.framework.components.web.jsp;

import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.multi.platform.filter.APPFilterConfig;
import j.jave.framework.components.web.multi.platform.filter.APPFilterConfigResolve;
import j.jave.framework.components.web.subhub.loginaccess.LoginAccessService;
import j.jave.framework.components.web.subhub.servlet.config.ServletConfigService;
import j.jave.framework.components.web.support.JFilter;
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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * the filter is only for JSP view , i.e. generally it's for the request sent from the <strong>browser client</strong>.
 * for any other platforms such as Android or IOS, from which the request never be intercepted. 
 * as so , you need be aware of the <strong>&lt;url-pattern></strong> , and
 * <strong>Note that some request based on servlet path need be forward. so you must configure servlet path ( {@link APPFilterConfig#SERVICE_ON_SERVLET_PATH} ),
 * <pre>
 * &lt;init-param>
 *			&lt;param-name>serviceServletPath&lt;/param-name>
 *			&lt;param-value>/web/service/dispatch/*&lt;/param-value>
 *		&lt;/init-param>
 * </pre>
 *  see APPFilterConfig</strong>
 *A sample below shown :<pre> &lt;filter>
 *		&lt;filter-name>JWebLoginFilter&lt;/filter-name>
 *		&lt;filter-class>j.jave.framework.components.web.jsp.JJSPLoginFilter&lt;/filter-class>
 *		&lt;init-param>
 *			&lt;param-name>serviceServletPath&lt;/param-name>
 *			&lt;param-value>/web/service/dispatch/*&lt;/param-value>
 *		&lt;/init-param>
 *	&lt;/filter>
 *	&lt;filter-mapping>
 *		&lt;filter-name>JWebLoginFilter&lt;/filter-name>
 *		&lt;url-pattern>/web/service/dispatch/*&lt;/url-pattern>
 *		&lt;dispatcher>FORWARD&lt;/dispatcher>
 *		&lt;dispatcher>REQUEST&lt;/dispatcher>
 *	&lt;/filter-mapping>
 *</pre>
 * @author J
 * @see {@link APPFilterConfig}
 */
public class JJSPLoginFilter implements JFilter ,APPFilterConfig {

	private static final Logger LOGGER=LoggerFactory.getLogger(JJSPLoginFilter.class);
	
	/**
	 * "/web/service/dispatch/*" pattern configured in web.xml . 
	 */
	private String serviceServletPath="/default";
	
	private ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private MemcachedService memcachedService= JServiceHubDelegate.get().getService(this,MemcachedService.class);;
	
	private LoginAccessService loginAccessService= JServiceHubDelegate.get().getService(this, LoginAccessService.class);
	
	
	/**
	 * default "/login.loginaction/login"
	 */
	private String serviceLoginPath=servletConfigService.getLoginPath();
	
	private String serviceToLoginPath=servletConfigService.getToLoginPath();
	
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
		
		try{
			HttpServletRequest req=(HttpServletRequest) request;
			
			// common resource , if path info is null or empty never intercepted by custom servlet.
			String target=HTTPUtils.getPathInfo(req);
			if(!loginAccessService.isNeedLoginRole(target)){
				// 资源不需要登录权限
				chain.doFilter(request, response);
				return ;
			}
			
			String clientTicket=HTTPUtils.getTicket(req);
			boolean isLogin=false;
			if(JStringUtils.isNullOrEmpty(clientTicket)){ // no login.
				isLogin=false;
			}
			else{ // check  whether server ticket is invalid. 
				HTTPContext serverTicket=(HTTPContext) memcachedService.get(clientTicket);
				if(serverTicket==null){
					isLogin=false;
				}
				else{
					isLogin=true;
				}
			}
			if(!isLogin){ // 没有登录， 跳转到登录页面
				req.getRequestDispatcher(this.serviceServletPath+serviceToLoginPath).forward(req, response);
			}
			else{ // 已经登录
				if(serviceLoginPath.equals(target)){  // 不能重复登录
					req.getRequestDispatcher(HTTPUtils.getAppUrlPath(req)).forward(req, response);
				}
				else{
					chain.doFilter(request, response);
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e); 
			throw new ServletException(e);
		}
	}

	@Override
	public void destroy() {
		
	}

}
