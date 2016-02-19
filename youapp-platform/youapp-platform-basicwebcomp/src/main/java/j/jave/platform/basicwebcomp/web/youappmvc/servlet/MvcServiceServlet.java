/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.servlet;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.platform.basicwebcomp.login.subhub.SessionUser;
import j.jave.platform.basicwebcomp.login.subhub.SessionUserGetEvent;
import j.jave.platform.basicwebcomp.web.support.JServlet;
import j.jave.platform.basicwebcomp.web.util.JCookieUtils;
import j.jave.platform.basicwebcomp.web.youappmvc.ViewConstants;
import j.jave.platform.basicwebcomp.web.youappmvc.action.ActionExecutor;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.JSONServletViewHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.jspview.JSPServletViewHandler;
import j.jave.platform.basicwebcomp.web.youappmvc.model.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.YouAppMvcUtils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * do with the request. 
 * a request accepted by the servlet should be like : 
 * "http://127.0.0.1:8686/youapp/web/service/dispatch/login.loginaction/toNavigate?name=J&age=99". 
 *  the different parts are described below:
 *  <p>protocol://host:port/app-context-path(if existing)/servlet-context-path(if existing)/action-bean-name(mandatory)/action-method(mandatory)?query-string
 *	<p>we first construct HTTP Context , then in which the request executed by ActionExecutor .
 *  the servlet also test the object from ActionExecutor is File(see {@link JFile}) or not, if true the response will be for downloading file,
 *  Note that we check that according to {@link JFile} ,but not any byte array {@link byte[]}. 
 * @author J
 * @see HttpContext
 * @see ActionExecutor#execute(HttpContext)
 * @see JSPServletViewHandler
 * @see JSONServletViewHandler
 */
@SuppressWarnings("serial")
public class MvcServiceServlet  extends JServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(MvcServiceServlet.class);
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	private JServletViewHandle servletViewHandle=new JSPServletViewHandler();
	
	public MvcServiceServlet() {
		LOGGER.info("Constructing JServiceServlet... ");
	}
	
	public static interface JServletViewHandle{
		
		/**
		 * how to handle navigate . the method is the end statement by the request. what is means the output stream 
		 * {@link HttpServletResponse#getOutputStream()} can be used.
		 * @param request
		 * @param response
		 * @param httpContext
		 * @param navigate
		 * @throws Exception
		 */
		public abstract void handleNavigate(HttpServletRequest request,HttpServletResponse response,
				HttpContext httpContext,Object navigate) throws Exception;
		
		/**
		 * how to handle service exception . the method is the end statement by the request. what is means the output stream 
		 * {@link HttpServletResponse#getOutputStream()} can be used.
		 * see {@link JServiceException}
		 * @param request
		 * @param response
		 * @param httpContext
		 * @param exception
		 */
		public abstract void handleServiceExcepion(HttpServletRequest request,HttpServletResponse response,HttpContext httpContext,JServiceException exception);
		
		/**
		 * how to handle exception .  the method is the end statement by the request. what is means the output stream 
		 * {@link HttpServletResponse#getOutputStream()} can be used.
		 * @param request
		 * @param response
		 * @param httpContext
		 * @param exception
		 */
		public abstract void handleExcepion(HttpServletRequest request,HttpServletResponse response,HttpContext httpContext,Exception exception);
		
	}
	
	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}
	
	
	/**
	 * process  /应用名/platform/模块名.服务名/方法名?查询参数
	 * <p>i.e. /youapp/platform/login.loginaction/login?userName='a'&password='b'
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		HttpContext httpContext=new HttpContext(req,resp);
		try{
			
			String uniqueName=YouAppMvcUtils.getTicket(req);
			httpContext.setTicket(uniqueName);
			
			if(JStringUtils.isNullOrEmpty(uniqueName)){
				//mock 用户信息
				SessionUser user=serviceHubDelegate.addImmediateEvent(new SessionUserGetEvent(this), SessionUser.class);
				String IP=YouAppMvcUtils.getIP(req);
				user.setUserName(IP);
				user.setId(IP);
				httpContext.setUser(user);
			}
			else{
				// 从会话缓存中获取信息
				HttpContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, uniqueName), HttpContext.class);
				if(context==null){
					JCookieUtils.deleteCookie(req, resp, JCookieUtils.getCookie(req, ViewConstants.TICKET));
					resp.sendRedirect(YouAppMvcUtils.getAppUrlPath(req));
					return ;
				}
				httpContext.setUser(context.getUser());
			}
			
			String target=YouAppMvcUtils.getPathInfo(req);
			httpContext.setTargetPath(target);
			Object navigate=ActionExecutor.newSingleExecutor().execute(httpContext);
			
			// if response for download.
			if(JFile.class.isInstance(navigate)){
				JFile file=(JFile)navigate;
				resp.setContentType("application/" + file.getFileExtension());
				resp.setContentLength((int) file.contentLength());
				resp.setHeader("Content-Disposition", "attachment;filename="+ file.getExpectedFullFileName()); 
				resp.getOutputStream().write(file.getFileContent());
			}
			else{
				servletViewHandle.handleNavigate(req, resp,httpContext, navigate);
			}

			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("the response of "+req.getRequestURL()+"[DispathType:"+req.getDispatcherType().name()+"] is OK!");
			}
		}
		catch(JServiceException e){
			LOGGER.error(e.getMessage(),e);
			servletViewHandle.handleServiceExcepion(req, resp, httpContext, e);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			servletViewHandle.handleExcepion(req, resp,httpContext,  e);
		}finally{
			if(JStringUtils.isNullOrEmpty(resp.getContentType())){
				resp.setContentType("text/html;charset=UTF-8");
			}
		}
		
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
