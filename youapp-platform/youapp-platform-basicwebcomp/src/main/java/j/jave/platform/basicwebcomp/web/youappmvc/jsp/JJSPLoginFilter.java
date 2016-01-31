package j.jave.platform.basicwebcomp.web.youappmvc.jsp;

import j.jave.kernal.jave.json.JJSON;
import j.jave.platform.basicwebcomp.web.support.JFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.filter.JLoginFilter;
import j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.support.APPFilterConfig;
import j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.support.FilterResponse;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
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
public class JJSPLoginFilter extends JLoginFilter implements JFilter ,APPFilterConfig {
	
	private String serviceToLoginPath=servletConfigService.getToLoginPath();
	
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		super.init(filterConfig);
		//add below 
	}
	
	@Override
	protected void handlerNoLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		request.setAttribute("url", serviceToLoginPath); 
		request.getRequestDispatcher("/WEB-INF/jsp/navigate.jsp").forward(request, response);
	}
	
	@Override
	protected void handlerDuplicateLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		FilterResponse filterResponse= FilterResponse.newDuplicateLogin();
		response.getOutputStream().write(JJSON.get().format(filterResponse).getBytes("utf-8"));
	}
	
	@Override
	protected void handlerToLogin(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception {
		request.getRequestDispatcher(request.getServletPath()+servletConfigService.getEntranceViewPath()).forward(request, response);
	}
	
	@Override
	public void destroy() {
		//add above
		
		super.destroy();
	}

}
