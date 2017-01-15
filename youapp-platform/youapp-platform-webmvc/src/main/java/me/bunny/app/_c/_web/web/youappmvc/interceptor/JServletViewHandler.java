package me.bunny.app._c._web.web.youappmvc.interceptor;

import me.bunny.app._c._web.web.youappmvc.HttpContext;
import me.bunny.app._c._web.web.youappmvc.RequestContext;
import me.bunny.app._c._web.web.youappmvc.ResponseContext;
import me.bunny.kernel.eventdriven.exception.JServiceException;

public interface JServletViewHandler {

	

	
	/**
	 * how to handle navigate . the method is the end statement by the request. what is means the output stream 
	 * {@link ResponseContext#getOutputStream()} can be used.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param navigate
	 * @throws Exception
	 */
	public abstract Object handleNavigate(RequestContext request,ResponseContext response,
			HttpContext httpContext,Object navigate) throws Exception;
	
	/**
	 * how to handle service exception . the method is the end statement by the request. what is means the output stream 
	 * {@link ResponseContext#getOutputStream()} can be used.
	 * see {@link JServiceException}
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	public abstract Object handleServiceExcepion(RequestContext request,ResponseContext response,HttpContext httpContext,JServiceException exception);
	
	/**
	 * how to handle exception .  the method is the end statement by the request. what is means the output stream 
	 * {@link ResponseContext#getOutputStream()} can be used.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	public abstract Object handleExcepion(RequestContext request,ResponseContext response,HttpContext httpContext,Exception exception);
	

	
}
