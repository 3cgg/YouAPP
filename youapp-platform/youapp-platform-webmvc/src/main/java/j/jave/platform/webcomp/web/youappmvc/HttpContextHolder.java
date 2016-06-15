package j.jave.platform.webcomp.web.youappmvc;

import j.jave.kernal.jave.model.JModel;
import j.jave.platform.webcomp.core.service.DefaultSessionUser;

/**
 * Http Context Holder.
 * @author J
 */
public class HttpContextHolder implements JModel {

	public static ThreadLocal<HttpContext> threadLocal=new ThreadLocal<HttpContext>();
	
	public static void set(HttpContext httpContext){
		threadLocal.set(httpContext);
	}
	
	public static HttpContext get(){
		return threadLocal.get();
	}
	
	public static void remove(){
		threadLocal.remove();
	}
	
	public static HttpContext getMockHttpContext(){
		HttpContext httpContext=new HttpContext();
		httpContext.setUser(DefaultSessionUser.getDefaultSessionUser());
		return httpContext;
	}
	
}
