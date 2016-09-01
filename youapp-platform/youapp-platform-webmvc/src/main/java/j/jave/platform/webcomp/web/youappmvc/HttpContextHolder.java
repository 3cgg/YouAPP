package j.jave.platform.webcomp.web.youappmvc;

import j.jave.kernal.jave.model.JModel;
import j.jave.platform.webcomp.core.service.DefaultSessionUser;

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
