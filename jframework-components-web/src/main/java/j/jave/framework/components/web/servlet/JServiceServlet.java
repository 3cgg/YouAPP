/**
 * 
 */
package j.jave.framework.components.web.servlet;

import j.jave.framework.components.web.ViewConstants;
import j.jave.framework.components.web.action.ActionExecutor;
import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.subhub.sessionuser.SessionUserGetEvent;
import j.jave.framework.components.web.subhub.sessionuser.SessionUser;
import j.jave.framework.components.web.support.JServlet;
import j.jave.framework.components.web.utils.CookieUtils;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.JServiceHubDelegate;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.servicehub.memcached.JMemcachedDisGetEvent;
import j.jave.framework.utils.JStringUtils;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
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
 * @author J
 * @see HTTPContext
 * @see ActionExecutor#execute(HTTPContext)
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
		HTTPContext httpContext=new HTTPContext();
		try{
			
			String uniqueName=HTTPUtils.getTicket(req);
			httpContext.setRequest(req);
			httpContext.setResponse(resp);
			httpContext.setTicket(uniqueName);
			
			if(JStringUtils.isNullOrEmpty(uniqueName)){
				//mock 用户信息
				SessionUser user=serviceHubDelegate.addImmediateEvent(new SessionUserGetEvent(this), SessionUser.class);
				String IP=HTTPUtils.getIP(req);
				user.setUserName(IP);
				user.setId(IP);
				httpContext.setUser(user);
			}
			else{
				// 从会话缓存中获取信息
				HTTPContext context=serviceHubDelegate.addImmediateEvent(new JMemcachedDisGetEvent(this, uniqueName), HTTPContext.class);
				if(context==null){
					CookieUtils.deleteCookie(req, resp, CookieUtils.getCookie(req, ViewConstants.TICKET));
					resp.sendRedirect(HTTPUtils.getAppUrlPath(req));
					return ;
				}
				httpContext.setUser(context.getUser());
			}
			
			// if the request content type is "multipart/form-data"
			if(HTTPUtils.isFileContextType(req)){
				 Map<String, Object> parameterValues=HTTPUtils.doWithRequestParameterWithFileAttached(req);
				 httpContext.setParameters(parameterValues);
			}
			
			String target=HTTPUtils.getPathInfo(req);
			httpContext.setTargetPath(target);
			Object navigate=ActionExecutor.newSingleExecutor().execute(httpContext);
			
			handlerNavigate(req, resp,httpContext, navigate);

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
			resp.setContentType("text/html;charset=UTF-8");
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
			HTTPContext httpContext,Object navigate) throws Exception;
	
	/**
	 * how to handle service exception . the method is the end statement by the request. what is means the output stream 
	 * {@link HttpServletResponse#getOutputStream()} can be used.
	 * see {@link JServiceException}
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerServiceExcepion(HttpServletRequest request,HttpServletResponse response,HTTPContext httpContext,JServiceException exception);
	
	/**
	 * how to handle exception .  the method is the end statement by the request. what is means the output stream 
	 * {@link HttpServletResponse#getOutputStream()} can be used.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param exception
	 */
	protected abstract void handlerExcepion(HttpServletRequest request,HttpServletResponse response,HTTPContext httpContext,Exception exception);
	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
	
}
