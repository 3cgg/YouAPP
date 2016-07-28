package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.RequestContext;
import j.jave.platform.webcomp.web.youappmvc.ResponseContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
