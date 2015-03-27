/**
 * 
 */
package j.jave.framework.components.views;

import j.jave.framework.components.core.context.SpringContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.hub.ServiceHub;
import j.jave.framework.components.core.hub.ServiceHub.StandardServiceInterfaces;
import j.jave.framework.components.file.model.FileDist;
import j.jave.framework.components.file.service.FileDistService;
import j.jave.framework.components.login.view.SessionUser;
import j.jave.framework.components.memcached.JMemcachedDistService;
import j.jave.framework.reflect.JReflect;
import j.jave.framework.utils.JUtils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoaderListener;

/**
 * @author Administrator
 *
 */
public class JDefaultServlet  extends HttpServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JDefaultServlet.class);
	
	private static ApplicationContext applicationContext=null;
	
	private static JMemcachedDistService jMemcachedDist= null;
	
	private static FileDistService fileDistService=null;
	
	public JDefaultServlet() {
		System.out.println("in  constructor DefaultJettyServlet() ");
	}
	
	
	
	/* (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@SuppressWarnings({ "static-access"})
	@Override
	public void init(ServletConfig config) throws ServletException {
		applicationContext=ContextLoaderListener.getCurrentWebApplicationContext();
		SpringContext.get().setApplicationContext(ContextLoaderListener.getCurrentWebApplicationContext()); 
		jMemcachedDist=ServiceHub.get().getService(StandardServiceInterfaces.MEMCACHED_DIST_SERVICE);
		applicationContext.getBean("fileDistServiceImpl",FileDistService.class);
		super.init(config);
	}
	
	
	/**
	 * process  /应用名/模块名.服务名/方法名?查询参数
	 * i.e. /youapp/login.loginaction/login?userName='a'&password='b'
	 */
	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			
			String contextPath=req.getContextPath()+req.getServletPath();
			String target=req.getRequestURI().substring(contextPath.length());
			
			String uniqueName=CookieUtils.getValue(req, "ticket");
			HTTPContext httpContext=new HTTPContext();
			httpContext.setRequest(req);
			httpContext.setResponse(resp);
			httpContext.setTicket(uniqueName);
			
			if(JUtils.isNullOrEmpty(uniqueName)){
				//mock 用户信息
				SessionUser user=new SessionUser();
				String IP=HTTPUtils.getIP(req);
				user.setUserName(IP);
				user.setId(IP);
				httpContext.setUser(user);
			}
			else{
				// 从会话缓存中获取信息
				HTTPContext context=(HTTPContext)jMemcachedDist.get(uniqueName);
				httpContext.setUser(context.getUser());
			}
			
			if (JUtils.isNotNullOrEmpty(req.getContentType())
					&&req.getContentType().indexOf("multipart/form-data") != -1) { // file
				Map<String, Object> parameters=new HashMap<String, Object>();
				httpContext.setParameters(parameters); 
				DiskFileItemFactory factory = new DiskFileItemFactory();
				factory.setSizeThreshold(1024 * 8);// 设置8k的缓存空间
				factory.setRepository(new File("d:/a"));
				ServletFileUpload upload = new ServletFileUpload(factory);
				upload.setHeaderEncoding("UTF-8");// 设置文件名处理中文编码
				try {
					FileItemIterator fii = upload.getItemIterator(req);// 使用遍历类
					while (fii.hasNext()) {
						FileItemStream fis = fii.next();
						if (fis.isFormField()) {// FileItemStream同样使用OpenStream获取普通表单的值
							String value=new String(JUtils.getBytes(fis.openStream()),"utf-8");
							parameters.put(fis.getFieldName(), value);
						} else {
							String fileName = fis.getName();
							byte[] bytes=JUtils.getBytes(fis.openStream());
							FileDist fileDist=new FileDist();
							fileDist.setBytes(bytes);
							fileDist.setFileName(fileName);
							URL url=fileDistService.distribute(fileDist);
							parameters.put(fis.getName(), url.toString());
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
			
			Method method=HTTPAction.class.getMethod("setHttpContext", HTTPContext.class);
			method.invoke(object, httpContext);
			//fillin attributes associate to the request. 
			set(object, req, httpContext);
			
			Object navigate=JReflect.invoke(object, methodName, new Object[]{});
			
			if(String.class.isInstance(navigate)){  // its forward to JSP. 
				String expectJsp=(String)navigate;
				if(expectJsp.endsWith(".jsp")){
					HTTPUtils.setHttpContext(req, httpContext);
					req.getRequestDispatcher(expectJsp).forward(req, resp); 
				}
			}
			
			System.out.println("time is :"+new Date().getTime());
		}
		catch(ServiceException e){
			req.setAttribute("message", e.getMessage());
			req.getRequestDispatcher("/WEB-INF/jsp/warning.jsp").forward(req, resp); 
		}
		catch(Exception e){
			Throwable exp=e;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(!ServiceException.class.isInstance(exp)){
				LOGGER.error(e.getMessage(), e); 
			}
			
			req.setAttribute("message", exp.getMessage());
			String expectJsp="/WEB-INF/jsp/error.jsp";
			if(ServiceException.class.isInstance(exp)){
				expectJsp="/WEB-INF/jsp/warning.jsp";
			}
			req.getRequestDispatcher(expectJsp).forward(req, resp); 
		}finally{
			resp.setContentType("text/html;charset=UTF-8");
		}
		
	}
	
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
	
	
	private void set(Object obj,String nameLink,Object valueObject) throws Exception{
		
		if(nameLink.indexOf(".")==-1 ) return ;  // no need fillin automatically 
		
		String[] names=new String[]{nameLink};
		if(nameLink.indexOf(".")!=-1){
			names=nameLink.split("[.]");
		}
		Object target=obj; 
		for (int i = 0; i < names.length; i++) {
			String name=names[i];
			Class superClass=target.getClass();
			Field field=null;
			while(superClass!=null&&field==null){
				try{
					field=superClass.getDeclaredField(name);
				}catch(NoSuchFieldException e){
					superClass=superClass.getSuperclass();
				}
			}
			
			if(field==null){
				throw new RuntimeException("["+name+"] attribute not found in "+target.getClass().getName());
			}
			
			field.setAccessible(true);
			if(i==names.length-1){  // the last one .  set value
				if(List.class.isInstance(valueObject)){ 
					field.set(target, valueObject);
				}
				else{
					String value=String.valueOf(valueObject);
					if(field.getType()==String.class){
						field.set(target, value);
					}
					else if(field.getType()==Double.class||field.getType()==double.class){
						field.set(target, JUtils.toDouble(value));
					}
					else if(field.getType()==Integer.class||field.getType()==int.class){
						field.set(target, JUtils.toInt(value));
					}
					else if(field.getType()==Long.class||field.getType()==long.class){
						field.set(target, JUtils.toLong(value));
					}
					else if(field.getType()==Timestamp.class){
						if(JUtils.isNotNullOrEmpty(value)){
							field.set(target, JUtils.parseTimestamp(value));
						}
					}
					else if(field.getType()==Date.class){
						if(JUtils.isNotNullOrEmpty(value)){
							field.set(target, JUtils.parseDate(value));
						}
					}
				}
			}
			else{  
				Object attributeObject=field.get(target);
				if(attributeObject==null){
					Object temp=field.getType().newInstance();
					field.set(target, temp);
					target=temp;
				}
				else{
					target=attributeObject;
				}
			}
		}
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
					set(obj, name, valuesObject);
				}
			}
			else{
				set(obj, name, value);
			}
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
