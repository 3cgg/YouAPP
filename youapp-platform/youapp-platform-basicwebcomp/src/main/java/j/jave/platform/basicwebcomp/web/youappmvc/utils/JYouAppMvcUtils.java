package j.jave.platform.basicwebcomp.web.youappmvc.utils;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.filedistribute.eventdriven.JFileDistStoreEvent;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicwebcomp.web.util.JCookieUtils;
import j.jave.platform.basicwebcomp.web.util.JWebUtils;
import j.jave.platform.basicwebcomp.web.youappmvc.ViewConstants;
import j.jave.platform.basicwebcomp.web.youappmvc.model.JHttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.support.JLinkedRequestSupport;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * HTTP Utilization
 * @author J
 */
public abstract class JYouAppMvcUtils extends JWebUtils {
	
	private static final Logger LOGGER=LoggerFactory.getLogger(JYouAppMvcUtils.class);

	private static final String YOUAPP_HTTP_CONTEXT_KEY="youAPPHttpContext";
	
	
	/**
	 * get {@link JHttpContext} from HTTP Request Scope.
	 * @param request
	 * @return
	 */
	public static final JHttpContext getHttpContext(HttpServletRequest request){
		return (JHttpContext) request.getAttribute(YOUAPP_HTTP_CONTEXT_KEY);
	}
	
	
	/**
	 * put the {@link JHttpContext} to HTTP Request Scope.
	 * @param request
	 * @param httpContext
	 */
	public static final void setHttpContext(HttpServletRequest request,JHttpContext httpContext ){
		request.setAttribute(YOUAPP_HTTP_CONTEXT_KEY, httpContext);
	}
	
	/**
	 * get ticket from cookie or request parameter of query string,
	 * get from cookie first , then from query parameter.
	 * <p>key of cookieis {@link ViewConstants #TICKET}
	 * <p>query parameter key is  {@link ViewConstants #TICKET_QUERY_PARAMETER}
	 * @param request
	 * @return
	 */
	public static final String getTicket(HttpServletRequest request){
		String ticket=JCookieUtils.getValue(request, ViewConstants.TICKET);
		if(ticket==null){
			ticket=request.getParameter(ViewConstants.TICKET_QUERY_PARAMETER);
		}
		return ticket;
	}
	
	/**
	 * remove ticket from the cookie context
	 * @param request
	 * @param response
	 */
	public static final void removeTicket(HttpServletRequest request,HttpServletResponse response){
		
		Cookie cookie=JCookieUtils.getCookie(request, ViewConstants.TICKET);
		if(cookie!=null){
			JCookieUtils.deleteCookie(request, response, cookie);
		}
	}

	/**
	 * set the object properties with the parameter keys of request query string,  
	 * the value of parameter is ordering by the {@link  JHttpContext#getParameter(String)}
	 *  , {@link HttpServletRequest#getParameter(String)} .
	 * @param obj
	 * @param request  if {@param httpContext}  does not contains the parameter, then get. <strong>{mandatory}</strong>
	 * @param httpContext  get value first.  <strong>{optional}</strong> 
	 * @throws Exception
	 * @see {@link JClassUtils#set}
	 */
	public static void set(Object obj, HttpServletRequest request, JHttpContext httpContext) throws Exception {
		if(request==null){
			throw new IllegalArgumentException("parameter request is null");
		}
		Enumeration<String> parameterNames=  request.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String name=parameterNames.nextElement();
			String value=null;
			if(httpContext!=null){
				value=httpContext.getParameter(name);
			}
			if(value==null){
				request.getParameter(name);
			}
			
			if(value==null){
				String[] values=null;
				if(httpContext!=null){
					values=httpContext.getParameterValues(name);
				}
				if(values==null){
					values=request.getParameterValues(name);
				}
				if(values!=null){
					List<String> valuesObject=new ArrayList<String>();
					for (int i = 0; i < values.length; i++) {
						String valueNoType=values[i];
						valuesObject.add(valueNoType);
					}
					JClassUtils.set(obj, name, valuesObject);
				}
			}
			else{
				JClassUtils.set(obj, name, value);
			}
		}
	}
	
	/**
	 * set the object properties with the request parameter. 
	 * @param obj
	 * @param request <strong>{mandatory}</strong>
	 * @throws Exception
	 * @see {@link JClassUtils#set}
	 */
	public static void set(Object obj, HttpServletRequest request) throws Exception {
		if(request==null){
			throw new IllegalArgumentException("parameter request is null");
		}
		set(obj, request, null);
	}
	
	/**
	 * set the object properties in the set of {@link JHttpContext#getParameters()}. 
	 * @param obj
	 * @param httpContext  <strong>argument is mandatory</strong>
	 * @throws Exception
	 */
	public static void set(Object obj, JHttpContext httpContext) throws Exception {
		if(httpContext==null){
			throw new IllegalArgumentException("parameter httpContext is null");
		}
		Map<String, Object> params=httpContext.getParameters();
		set(obj, params); 
	}
	
	/**
	 * set the object properties from the map.
	 * @param obj
	 * @param params
	 * @throws Exception
	 */
	public static void set(Object obj, Map<String, Object> params) throws Exception {
		Iterator<Entry<String,Object>> parameterNames=  params.entrySet().iterator();;
		while(parameterNames.hasNext()){
			Entry<String,Object> param=parameterNames.next();
			String name=param.getKey();
			Object value= param.getValue();
			JClassUtils.set(obj, name, value);
		}
	}
	
	
	/**
	 * do with request , to extract file which send to file distribute service . 
	 * @param request
	 * @return Map<String, Object>  , empty object, if the request type is not "multipart/form-data" 
	 * or the request does not have  any parameter.  
	 * <p> KEY: parameter key.
	 * <p> VALUE: parameter value(s) 
	 */
	public static Map<String, Object> doWithRequestParameterWithFileAttached( HttpServletRequest request) {
		Map<String, Object> parameters=new HashMap<String, Object>();
		if (isFileContextType(request)) { // file
//			DiskFileItemFactory factory = new DiskFileItemFactory();
//			factory.setSizeThreshold(1024 * 8);// 设置8k的缓存空间
//			factory.setRepository(new File("d:/a"));
			ServletFileUpload upload = new ServletFileUpload();
			upload.setHeaderEncoding("utf-8");// 设置文件名处理中文编码
			try {
				FileItemIterator fii = upload.getItemIterator(request);// 使用遍历类
				while (fii.hasNext()) {
					FileItemStream fis = fii.next();
					if (fis.isFormField()) {// FileItemStream同样使用OpenStream获取普通表单的值
						String value=new String(JStringUtils.getBytes(fis.openStream()),"utf-8");
						parameters.put(fis.getFieldName(), value);
					} else {
						String fileName = fis.getName();
						byte[] bytes=JStringUtils.getBytes(fis.openStream());
						File file=new File(fileName);
						JFile jFile=new JFile(file);
						jFile.setFileContent(bytes);
						
						URL url=JServiceHubDelegate.get().addImmediateEvent(new JFileDistStoreEvent(request, jFile),URI.class).toURL();
						parameters.put(fis.getFieldName(), url.toString());
					}
				}
			} catch (FileUploadException e) {
				LOGGER.error(e.getMessage(), e);
			}catch (IOException e) {
				LOGGER.error(e.getMessage(), e);
			}
		}
		return parameters;
	}
	
	
	/**
	 * put additional attributes in the request scope, these attributes are only added by framework. 
	 * all ones should be in the only one map with the constraint key {@link JHttpContext#ADDITIONAL_PARAM_KEY}
	 * <strong>Note that the individual module should not call the method.</strong>, i.e. the method is inner only for framework.
	 * @param request
	 * @param key
	 * @param attributes
	 * @return
	 */
	public static Object setAdditionalAttributesInRequestScope(HttpServletRequest request,String key,Object attributes){
		Object previous=null;
		Map<String, Object> object=(Map<String, Object>) request.getAttribute(JHttpContext.ADDITIONAL_PARAM_KEY);
		if(object==null){
			object=new HashMap<String, Object>();
			object.put(key, attributes);
			request.setAttribute(JHttpContext.ADDITIONAL_PARAM_KEY, object);
			previous=attributes;
		}
		else{
			previous=object.put(key, attributes);
		}
		return previous;
	}
	
	/**
	 * get additional attributes in the request scope.these attributes are only used by framework. 
	 * all ones should be in the only one map with the constraint key {@link JHttpContext#ADDITIONAL_PARAM_KEY}
	 * <strong>Note that the individual module should not call the method.</strong>, i.e. the method is inner only for framework.
	 * @param request
	 * @param key
	 * @param attributes
	 * @return
	 */
	public static Object getAdditionalAttributesInRequestScope(HttpServletRequest request,String key){
		Map<String, Object> object=(Map<String, Object>) request.getAttribute(JHttpContext.ADDITIONAL_PARAM_KEY);
		if(object==null){
			return null;
		}
		else{
			return object.get(key);
		}
	}
	
	/**
	 * get parameter value from query string part.
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getParameter(HttpServletRequest request,String key) {
		
		if(request==null) return null;
		String value = null;
		if(JLinkedRequestSupport.isLinked(request)){
			value=JLinkedRequestSupport.getParameter(request, key);
		}
		else{
			value = request.getParameter(key);
		}
		return value;
	}
	
	/**
	 * get parameter values from query string part.
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getParameterValues(HttpServletRequest request,String key) {
		if(request==null) return null;
		String[] values = null;
		if(JLinkedRequestSupport.isLinked(request)){
			values=JLinkedRequestSupport.getParameterValues(request, key);
		}
		else{
			values = request.getParameterValues(key);
		}
		return values;
	}
	
}
