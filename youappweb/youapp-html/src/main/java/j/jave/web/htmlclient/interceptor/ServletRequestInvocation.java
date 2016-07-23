package j.jave.web.htmlclient.interceptor;

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
	
	public void setUnique(String unique);
	
	public String getUnique();
	
	
}
