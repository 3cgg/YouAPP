package j.jave.web.htmlclient.interceptor;


/**
 * 
 * @author J
 *
 */
public interface ServletRequestInterceptor {

	public abstract Object intercept(ServletRequestInvocation servletRequestInvocation);
	
}
