package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

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
	public Object proceed();
	
	public HttpServletRequest getHttpServletRequest();

	public HttpServletResponse getHttpServletResponse();
	
	HttpContext getHttpContext();
	
	void setHttpContext(HttpContext httpContext);
}
