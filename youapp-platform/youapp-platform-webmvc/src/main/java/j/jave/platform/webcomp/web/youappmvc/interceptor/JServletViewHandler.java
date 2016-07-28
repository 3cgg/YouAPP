package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.RequestContext;
import j.jave.platform.webcomp.web.youappmvc.ResponseContext;

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
