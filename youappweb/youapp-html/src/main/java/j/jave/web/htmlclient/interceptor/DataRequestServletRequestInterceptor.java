package j.jave.web.htmlclient.interceptor;


/**
 * 
 * @author J
 *
 */
public interface DataRequestServletRequestInterceptor extends ServletRequestInterceptor {

	public abstract Object intercept(DataRequestServletRequestInvocation servletRequestInvocation);
	
}
