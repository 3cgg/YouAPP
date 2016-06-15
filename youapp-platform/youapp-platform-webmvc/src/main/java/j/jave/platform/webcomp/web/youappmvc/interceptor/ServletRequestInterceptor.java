package j.jave.platform.webcomp.web.youappmvc.interceptor;


/**
 * 
 * @author J
 *
 */
public interface ServletRequestInterceptor {

	public abstract Object intercept(ServletRequestInvocation servletRequestInvocation);
	
}
