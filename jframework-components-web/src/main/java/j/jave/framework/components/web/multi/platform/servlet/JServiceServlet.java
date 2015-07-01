/**
 * 
 */
package j.jave.framework.components.web.multi.platform.servlet;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.memcached.eventdriven.JMemcachedDisGetEvent;
import j.jave.framework.commons.utils.JStringUtils;
import j.jave.framework.components.web.ViewConstants;
import j.jave.framework.components.web.action.ActionExecutor;
import j.jave.framework.components.web.jsp.JJSPServiceServlet;
import j.jave.framework.components.web.mobile.JMobileServiceServlet;
import j.jave.framework.components.web.model.JHttpContext;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserGetEvent;
import j.jave.framework.components.web.support.JServlet;
import j.jave.framework.components.web.utils.JCookieUtils;
import j.jave.framework.components.web.utils.JHttpUtils;

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
 * @see JHttpContext
 * @see ActionExecutor#execute(JHttpContext)
 * @see JJSPServiceServlet
 * @see JMobileServiceServlet
 */
@SuppressWarnings("serial")
public abstract class JServiceServlet  extends JServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JServiceServlet.class);
	
	private JServiceHubDelegate serviceHubDelegate=JServiceHubDelegate.get();
	
	public JServiceServlet() {
		LOGGER.info("in  constructor DefaultJettyServlet() ");
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
		JHttpContext httpContext=new JHttpContext(req,resp);
		try{
			
			String uniqueName=JHttpUtils.getTicket(req);
			httpContext.setTicket(uniqueName);
			
			if(JStringUtils.isNullOrEmpty(uniqueName)){
				//mock 用户信息
				SessionUser user=serviceHubDelegate.addImmediateEvent(new SessionUserGetEvent(this), SessionUser.class);
				String IP=JHttpUtils.getIP(req);
				user.setUserName(IP);
				user.setId(IP);
				httpContext.setUser(user);
			}
			else{
				// 从会话缓存中获取信息
				JHttpContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, uniqueName), JHttpContext.class);
				if(context==null){
					JCookieUtils.deleteCookie(req, resp, JCookieUtils.getCookie(req, ViewConstants.TICKET));
					resp.sendRedirect(JHttpUtils.getAppUrlPath(req));
					return ;
				}
				httpContext.setUser(context.getUser());
			}
			
			String target=JHttpUtils.getPathInfo(req);
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
				handlerNavigate(req, resp,httpContext, navigate);
			}

			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("the response of "+req.getRequestURL()+"[DispathType:"+req.getDispatcherType().name()+"] is OK!");
			}
		}
		catch(JServiceException e){
			LOGGER.error(e.getMessage(),e);
			handlerServiceExcepion(req, resp, httpContext, e);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			handlerExcepion(req, resp,httpContext,  e);
		}finally{
			if(JStringUtils.isNullOrEmpty(resp.getContentType())){
				resp.setContentType("text/html;charset=UTF-8");
			}
		}
		
	}
	
	/**
	 * how to handle navigate . the method is the end statement by the request. what is means the output stream 
	 * {@link HttpServletResponse#getOutputStream()} can be used.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param navigate
	 * @throws Exception
	 */
	protected abstract void handlerNavigate(HttpServletRequest request,HttpServletResponse response,
			JHttpContext httpContext,Object navigate) throws Exception;
	
	/**
	 * how to handle service exception . the method is the end statement by the request. what is means the output stream 
	 * {@link HttpServletResponse#getOutputStream()} can be used.
	 * see {@link JServiceException}
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerServiceExcepion(HttpServletRequest request,HttpServletResponse response,JHttpContext httpContext,JServiceException exception);
	
	/**
	 * how to handle exception .  the method is the end statement by the request. what is means the output stream 
	 * {@link HttpServletResponse#getOutputStream()} can be used.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerExcepion(HttpServletRequest request,HttpServletResponse response,JHttpContext httpContext,Exception exception);
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
