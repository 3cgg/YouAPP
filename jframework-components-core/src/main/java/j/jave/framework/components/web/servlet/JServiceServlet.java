/**
 * 
 */
package j.jave.framework.components.web.servlet;

import j.jave.framework.components.core.context.SpringContext;
import j.jave.framework.components.core.servicehub.ServiceHubDelegate;
import j.jave.framework.components.login.view.SessionUser;
import j.jave.framework.components.support.filedistribute.subhub.FileDisService;
import j.jave.framework.components.support.memcached.subhub.MemcachedService;
import j.jave.framework.components.web.ViewConstants;
import j.jave.framework.components.web.action.AbstractAction;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.utils.CookieUtils;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.io.JFile;
import j.jave.framework.reflect.JClassUtils;
import j.jave.framework.reflect.JReflect;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.utils.JDateUtils;
import j.jave.framework.utils.JStringUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

/**
 * do with the request. 
 * @author J
 */
public abstract class JServiceServlet  extends HttpServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JServiceServlet.class);
	
	private static ApplicationContext applicationContext=null;
	
	private static MemcachedService memcachedService= null;
	
	private static FileDisService fileDistService=null;
	
	public JServiceServlet() {
		System.out.println("in  constructor DefaultJettyServlet() ");
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		applicationContext=ContextLoaderListener.getCurrentWebApplicationContext();
		SpringContext.get().setApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext()); 
		memcachedService=ServiceHubDelegate.get().getService(this,MemcachedService.class);
		fileDistService=ServiceHubDelegate.get().getService(this,FileDisService.class);
		super.init(config);
	}
	
	
	/**
	 * process  /应用名/platform/模块名.服务名/方法名?查询参数
	 * <p>i.e. /youapp/platform/login.loginaction/login?userName='a'&password='b'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HTTPContext httpContext=new HTTPContext();
		try{
			
			String contextPath=req.getContextPath()+req.getServletPath();
			String target=req.getRequestURI().substring(contextPath.length());
			
			String uniqueName=HTTPUtils.getTicket(req);
			
			httpContext.setRequest(req);
			httpContext.setResponse(resp);
			httpContext.setTicket(uniqueName);
			
			if(JStringUtils.isNullOrEmpty(uniqueName)){
				//mock 用户信息
				SessionUser user=new SessionUser();
				String IP=HTTPUtils.getIP(req);
				user.setUserName(IP);
				user.setId(IP);
				httpContext.setUser(user);
			}
			else{
				// 从会话缓存中获取信息
				HTTPContext context=(HTTPContext)memcachedService.get(uniqueName);
				if(context==null){
					CookieUtils.deleteCookie(req, resp, CookieUtils.getCookie(req, ViewConstants.TICKET));
					resp.sendRedirect(getAppUrlPath(req));
					return ;
				}
				httpContext.setUser(context.getUser());
			}
			
			if (JStringUtils.isNotNullOrEmpty(req.getContentType())
					&&req.getContentType().indexOf("multipart/form-data") != -1) { // file
				Map<String, Object> parameters=new HashMap<String, Object>();
				httpContext.setParameters(parameters); 
//				DiskFileItemFactory factory = new DiskFileItemFactory();
//				factory.setSizeThreshold(1024 * 8);// 设置8k的缓存空间
//				factory.setRepository(new File("d:/a"));
				ServletFileUpload upload = new ServletFileUpload();
				upload.setHeaderEncoding("UTF-8");// 设置文件名处理中文编码
				try {
					FileItemIterator fii = upload.getItemIterator(req);// 使用遍历类
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
							
							URL url=fileDistService.distribute(jFile).toURL();
							parameters.put(fis.getFieldName(), url.toString());
						}
					}
				} catch (FileUploadException e) {
					LOGGER.error(e.getMessage(), e);
				}
			}
			
			String serviceName="";;
			String methodName="";
			if(target.startsWith("/")) target=target.substring(1);
			String[] targets=target.split("/");
			serviceName=targets[0];
			methodName=targets[1];
			
			Object object=applicationContext.getBean(serviceName);
			
			Method method=AbstractAction.class.getMethod("setHttpContext", HTTPContext.class);
			method.invoke(object, httpContext);
			//fill in attributes associate to the request. 
			set(object, req, httpContext);
			
			StopWatch stopWatch=null;
			if(LOGGER.isDebugEnabled()){
				stopWatch=new StopWatch();
				stopWatch.start();
			}
			Object navigate=JReflect.invoke(object, methodName, new Object[]{});
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("time of processing request is :"+JDateUtils.getTimeOffset(stopWatch.getTime()));
			}
			handlerNavigate(req, resp,httpContext, navigate);

		}
		catch(JServiceException e){
			handlerServiceExcepion(req, resp, httpContext, e);
		}
		catch(Exception e){
			handlerExcepion(req, resp,httpContext,  e);
		}finally{
			resp.setContentType("text/html;charset=UTF-8");
		}
		
	}
	
	/**
	 * how to handle navigate
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param navigate
	 * @throws Exception
	 */
	protected abstract void handlerNavigate(HttpServletRequest request,HttpServletResponse response,
			HTTPContext httpContext,Object navigate) throws Exception;
	
	/**
	 * how to handle service exception 
	 * see {@link JServiceException}
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerServiceExcepion(HttpServletRequest request,HttpServletResponse response,HTTPContext httpContext,JServiceException exception);
	
	/**
	 * how to handle exception . 
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerExcepion(HttpServletRequest request,HttpServletResponse response,HTTPContext httpContext,Exception exception);
	
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
	
	protected String getAppUrlPath(HttpServletRequest request){
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
	
	private void set(Object obj, HttpServletRequest req, HTTPContext httpContext) throws Exception {
		Enumeration<String> parameterNames=  req.getParameterNames();
		while(parameterNames.hasMoreElements()){
			String name=parameterNames.nextElement();
			String value=httpContext.getParameter(name);
			if(value==null){
				String[] values=httpContext.getParameterValues(name);
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
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
