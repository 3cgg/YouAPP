package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AuthenticationHandler {


	/**
	 * how to do with no authorized access to a resource
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleNoLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * loginout
	 * @param request
	 * @param response
	 * @param httpContext
	 * @throws Exception
	 */
	public Object handleLoginout(HttpServletRequest request, HttpServletResponse response,HttpContext httpContext) throws Exception;
	
	
	/**
	 * how to do with login more once.
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleDuplicateLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	/**
	 * how to do with to login request, the request is force the end-user go the login page.
	 * @param request
	 * @param response
	 * @param chain
	 */
	@Deprecated
	public void handleToLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;
	
	/**
	 * try to login the system, then response the end-user with login status.
	 * @param request
	 * @param response
	 * @param chain
	 */
	public Object handleLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
	public Object handleExpiredLogin(HttpServletRequest request, HttpServletResponse response) throws Exception;
	
}
