package j.jave.framework.components.views;

import j.jave.framework.utils.JUtils;

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
		if(JUtils.isNotNullOrEmpty(contentPath)){
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
	
}
