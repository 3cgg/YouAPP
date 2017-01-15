package me.bunny.app._c._web.web.youappmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c._web.web.youappmvc.RequestContext;
import me.bunny.app._c._web.web.youappmvc.ResponseContext;

/**
 * 
 * @author J
 */
public interface ServletRequestInvocation {

	/**
	 * progress of executing intercepters
	 * @return
	 */
	public Object proceed()  throws Throwable;
	
	@Deprecated
	public HttpServletRequest getHttpServletRequest();

	@Deprecated
	public HttpServletResponse getHttpServletResponse();
	
	public RequestContext getRequestContext();
	
	public ResponseContext getResponseContext();
	
	HttpContext getHttpContext();
	
	void setHttpContext(HttpContext httpContext);
	
}
