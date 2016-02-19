package j.jave.platform.basicwebcomp.web.youappmvc.jspview;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.json.JJSON;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.FilterResponse;
import j.jave.platform.basicwebcomp.web.youappmvc.filter.LoginFilter.LoginHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.subhub.servletconfig.ServletConfigService;
import j.jave.platform.basicwebcomp.web.youappmvc.support.APPFilterConfig;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
public class JSPLoginFilter implements LoginHandler ,APPFilterConfig {
	
	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
	
	private String serviceToLoginPath=servletConfigService.getToLoginPath();
	
	@Override
	public void handleNoLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		request.setAttribute("url", serviceToLoginPath); 
		request.getRequestDispatcher("/WEB-INF/jsp/navigate.jsp").forward(request, response);
	}
	
	@Override
	public void handleDuplicateLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newDuplicateLogin();
		response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
	}
	
	@Override
	public void handleToLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		request.getRequestDispatcher(request.getServletPath()+servletConfigService.getEntranceViewPath()).forward(request, response);
	}

}
