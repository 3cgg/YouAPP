package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;


/**
 * 
 * @author J
 *
 */
public interface ServletRequestInterceptor {

	public abstract Object intercept(ServletRequestInvocation servletRequestInvocation);
	
}
