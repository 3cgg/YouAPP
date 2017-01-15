package j.jave.platform.webcomp.web.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.DispatcherType;
import javax.servlet.http.HttpServletRequest;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.utils.JStringUtils;
import me.bunny.kernel._c.utils.JUtilException;


/**
 * Web helper
 * @author J
 */
public abstract class JWebUtils {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JWebUtils.class);

	/**
	 * get remote client IP address.
	 * @param request
	 * @return
	 */
	public static final String getIP(HttpServletRequest request){
		String ip="";
		ip = request.getRemoteAddr();
		return ip;
	}
	
	/**
	 * get client the end user send the request.
	 * @param request
	 * @return
	 */
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
	
	/**
	 * check whether the request is a resource , i.e.  JS , CSS or image.
	 * @param request
	 * @return
	 */
	public static boolean isResource(HttpServletRequest request){
		return false;
	}
	
	/**
	 * get path info 
	 * @see HttpServletRequest #getPathInfo()
	 * @param request
	 * @return
	 */
	public static String getPathInfo(HttpServletRequest request){
		return request.getPathInfo();
	}
	
	/**
	 * get context path (if any) plus servlet path (if any). 
	 * @param request
	 * @return
	 */
	public static String getContextPahPlusServletPath(HttpServletRequest request){
		return request.getContextPath()+request.getServletPath();
	}

	/**
	 * check if the context type is "multipart/form-data" , i.e. file upload...
	 * @param request
	 * @return
	 */
	public static boolean isFileContextType( HttpServletRequest request){
		return (JStringUtils.isNotNullOrEmpty(request.getContentType())
				&&request.getContentType().indexOf("multipart/form-data") != -1);
	}
	
	/**
	 * decode the query string
	 * @param queryString
	 * @return
	 */
	public static String decode(String queryString){
		try {
			return URLDecoder.decode(queryString,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new JUtilException(e);
		}
	}
	
	/**
	 * decode the query string
	 * @param queryString
	 * @return
	 */
	public static String encode(String queryString){
		try {
			return URLEncoder.encode(queryString,"utf-8");
		} catch (UnsupportedEncodingException e) {
			throw new JUtilException(e);
		}
	}
	
	/**
	 * parse query string to map instance.
	 * @param queryString
	 * @return
	 */
	public static  Map<String, String> parseQueryString(String queryString){
		Map<String, String> params=new HashMap<String, String>();
		String[] parameters=queryString.split("&");
		if(parameters!=null){
			for(int i=0;i<parameters.length;i++){
				String param=parameters[i];
				if(JStringUtils.isNotNullOrEmpty(param)){
					String[] keyValue=param.split("=");
					if(keyValue.length!=2){
						throw new JUtilException("query parameter is invalid."+queryString);
					}
					params.put(keyValue[0], keyValue[1]);
				}
			}
		}
		return params;
	}
	
	public static boolean isRequestTypeAndGET(HttpServletRequest request){
		return request.getDispatcherType()==DispatcherType.REQUEST
				&&"GET".equals(request.getMethod());
	}

	/**
	 * parse the parameter in the query string or posted form data.
	 * @param request
	 * @return
	 */
	public static Map<String, Object> parseRequestParameters(HttpServletRequest request){
		Map<String, Object> params=new HashMap<String, Object>();
		Map<String, String[]> paramMap =  request.getParameterMap();
		for (Iterator<Entry<String, String[]>> iterator = paramMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, String[]> entry =  iterator.next();
			String[] values=entry.getValue();
			params.put(entry.getKey(), values.length==1?values[0]:values);
		}
		return params;
	}
	
	
	/**
	 * all head map form.
	 * @param request
	 * @param splitString the split string for multiple values of certain head.
	 * @return
	 */
	public static Map<String, String> parseRequstHeads(HttpServletRequest request,String... splitStrings){
		Map<String, String> params=new HashMap<String, String>();
		Enumeration<String> headNames= request.getHeaderNames();
		String splitString=splitStrings.length>1?splitStrings[0]:";";
		while(headNames.hasMoreElements()){
			String headName=headNames.nextElement();
			Enumeration<String> values= request.getHeaders(headName);
			String val="";
			while(values.hasMoreElements()){
				val=splitString+values.nextElement();
			}
			val=val.replaceFirst(splitString, "");
			params.put(headName, val);
		}
		return params;
	}
	
	
	
	
	
}
