package me.bunny.app._c._web.web.youappmvc;

import me.bunny.app._c._web.core.service.DefaultSessionUser;
import me.bunny.kernel._c.model.JModel;

/**
 * Http Context Holder.
 * @author J
 */
public class HttpContextHolder implements JModel {

	public static final ThreadLocal<HttpContext> THREAD_LOCAL=new ThreadLocal<HttpContext>();
	
	public static void set(HttpContext httpContext){
		THREAD_LOCAL.set(httpContext);
	}
	
	public static HttpContext get(){
		return THREAD_LOCAL.get();
	}
	
	public static void remove(){
		THREAD_LOCAL.remove();
	}
	
	public static HttpContext getMockHttpContext(){
		ServletHttpContext httpContext=new ServletHttpContext();
		httpContext.setUser(DefaultSessionUser.getDefaultSessionUser());
		return httpContext;
	}
	
}
