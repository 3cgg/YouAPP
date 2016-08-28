package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;

public interface AuthenticationHandler {


	/**
	 * how to do with no authorized access to a resource
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleNoLogin(HttpContext httpContext) throws Exception;
	
	/**
	 * loginout
	 * @param request
	 * @param response
	 * @param httpContext
	 * @throws Exception
	 */
	public Object handleLoginout(HttpContext httpContext) throws Exception;
	
	/**
	 * how to do with login more once.
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleDuplicateLogin(HttpContext httpContext) throws Exception;
	
	/**
	 * try to login the system, then response the end-user with login status.
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleLogin(HttpContext httpContext) throws Exception;
	
	public Object handleExpiredLogin(HttpContext httpContext) throws Exception;
	
}
