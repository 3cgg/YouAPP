package me.bunny.app._c._web.web.youappmvc.jspview;

import me.bunny.app._c._web.web.youappmvc.support.APPFilterConfig;

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
@Deprecated
public class JSPLoginHandler  {
//	
//	
//	protected ServletConfigService servletConfigService=JServiceHubDelegate.get().getService(this, ServletConfigService.class);
//	
//	@Override
//	public void handleNoLogin(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain) throws Exception {
////		request.setAttribute("url", serviceToLoginPath); 
////		request.getRequestDispatcher("/WEB-INF/jsp/navigate.jsp").forward(request, response);
//		request.getRequestDispatcher(request.getServletPath()+servletConfigService.getToLoginPath()).forward(request, response);
//	}
//	
//	@Override
//	public void handleDuplicateLogin(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain) throws Exception {
//		ResponseModel filterResponse= ResponseModel.newDuplicateLogin();
//		response.getOutputStream().write(JJSON.get().formatObject(filterResponse).getBytes("utf-8"));
//	}
//	
//	@Override
//	public void handleToLogin(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain) throws Exception {
//		request.getRequestDispatcher(request.getServletPath()+servletConfigService.getEntranceViewPath()).forward(request, response);
//	}
//
//	@Override
//	public void handleLogin(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain) throws Exception {
//		
//	}
//	
//	@Override
//	public void handleExpiredLogin(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain) throws Exception {
//	}
//	
//	@Override
//	public void handleLoginout(HttpServletRequest request,
//			HttpServletResponse response, FilterChain chain,
//			HttpContext httpContext) throws Exception {
//	}
}
