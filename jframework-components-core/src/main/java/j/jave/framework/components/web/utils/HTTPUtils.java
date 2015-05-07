package j.jave.framework.components.web.utils;

import j.jave.framework.components.web.ViewConstants;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.utils.JStringUtils;

import javax.servlet.http.HttpServletRequest;

public class HTTPUtils {

	public static final String getIP(HttpServletRequest request){
		String ip="";
		ip = request.getRemoteAddr();
		return ip;
	}
	
	public static final String getClient(HttpServletRequest request){
		String client="";
		client=request.getHeader("USER-AGENT");
		return client;
	}
	
	/**
	 * return http://host:port/app-content-path
	 * @param request
	 * @return
	 */
	public static final String getAppUrlPath(HttpServletRequest request){
		String appUrlPath="";
		String contentPath=request.getContextPath();
		String url=request.getRequestURL().toString();
		if(JStringUtils.isNotNullOrEmpty(contentPath)){
			appUrlPath= url.substring(0,url.indexOf(contentPath))+contentPath;
		}
		else{
			String uri=request.getRequestURI().toString();
			if("/".equals(uri)){
				appUrlPath=url.substring(0,url.length()-1);
			}
			else{
				appUrlPath=url.substring(0,url.indexOf(uri));
			}
		}
		return appUrlPath;
	}
	
	private static final String YOUAPP_SESSION_USER_KEY="youAPPSessionUser";
	
	private static final String YOUAPP_HTTP_CONTEXT_KEY="youAPPHttpContext";
	
	public static final HTTPContext getHttpContext(HttpServletRequest request){
		return (HTTPContext) request.getAttribute(YOUAPP_HTTP_CONTEXT_KEY);
	}
	
	public static final void setHttpContext(HttpServletRequest request,HTTPContext httpContext ){
		request.setAttribute(YOUAPP_HTTP_CONTEXT_KEY, httpContext);
	}
	
	public static final String getTicket(HttpServletRequest request){
		String ticket=CookieUtils.getValue(request, ViewConstants.TICKET);
		if(ticket==null){
			ticket=request.getParameter(ViewConstants.TICKET);
		}
		return ticket;
	}
	
	/**
	 * check whether the request is a resource , i.e.  JS , CSS or image.
	 * @param request
	 * @return
	 */
	public static boolean isResource(HttpServletRequest request){
		return false;
	}
	
	public static String getPathInfo(HttpServletRequest request){
		return request.getPathInfo();
	}
	
	
	
	
	
	
}
