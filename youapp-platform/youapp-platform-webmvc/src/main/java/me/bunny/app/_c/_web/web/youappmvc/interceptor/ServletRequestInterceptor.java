package me.bunny.app._c._web.web.youappmvc.interceptor;


/**
 * 
 * @author J
 *
 */
public interface ServletRequestInterceptor {

	public abstract Object intercept(ServletRequestInvocation servletRequestInvocation) throws Throwable;
	
}
