package j.jave.platform.basicwebcomp.web.youappmvc.filter;

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
	public void handleNoLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;
	
	/**
	 * how to do with login more once.
	 * @param request
	 * @param response
	 * @param chain
	 */
	public void handleDuplicateLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;
	
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
	public void handleLogin(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;

	
}
