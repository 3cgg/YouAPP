package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;

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
	
	public void setUnique(String unique);
	
	public String getUnique();
	
	public void setMappingPath(String mappingPath);
	
	public String getMappingPath();
	
}
