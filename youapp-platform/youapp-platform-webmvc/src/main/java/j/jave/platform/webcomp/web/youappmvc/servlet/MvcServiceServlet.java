/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.servlet;

import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.support.JServlet;
import j.jave.platform.webcomp.web.util.JResponseWrittenRejectWrapper;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerExecutor;
import j.jave.platform.webcomp.web.youappmvc.interceptor.DefaultServletRequestInvocation;
import j.jave.platform.webcomp.web.youappmvc.interceptor.ServletExceptionUtil;
import j.jave.platform.webcomp.web.youappmvc.interceptor.ServletRequestInvocation;
import j.jave.platform.webcomp.web.youappmvc.jsonview.HttpServletResponseUtil;
import j.jave.platform.webcomp.web.youappmvc.jsonview.JSONServletViewHandler;
import me.bunny.kernel.jave.io.JFile;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.utils.JStringUtils;

import java.io.IOException;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
 * @see ServletHttpContext
 * @see ControllerExecutor#execute(ServletHttpContext)
 * @see JSONServletViewHandler
 */
@SuppressWarnings("serial")
public class MvcServiceServlet  extends JServlet {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(MvcServiceServlet.class);
	
	public MvcServiceServlet() {
		LOGGER.info("setup servlet :  "+MvcServiceServlet.class.getName()+"... ");
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
		HttpServletRequest request=req;
		HttpServletResponse response=resp;
		try{
			JResponseWrittenRejectWrapper rejectWrapper=new JResponseWrittenRejectWrapper(resp);
			ServletRequestInvocation servletRequestInvocation=new DefaultServletRequestInvocation(req, rejectWrapper);
			Object navigate=servletRequestInvocation.proceed();
			// if response for download.
			if(JFile.class.isInstance(navigate)){
				JFile file=(JFile)navigate;
				response.setContentType("application/" + file.getFileExtension());
				response.setContentLength((int) file.contentLength());
				response.setHeader("Content-Disposition", "attachment;filename="+ file.getExpectedFullFileName()); 
				HttpServletResponseUtil.writeBytesDirectly(request, response, file.getFileContent());
			}
			else if(ResponseModel.class.isInstance(navigate)){
				HttpServletResponseUtil.write(request, response, servletRequestInvocation.getHttpContext(), navigate);
			}
			else{
				HttpServletResponseUtil.write(request, response, servletRequestInvocation.getHttpContext(), navigate);
			}

			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("the response of "+req.getRequestURL()+"[DispathType:"+req.getDispatcherType().name()+"] is OK!");
			}
		}
		catch(Throwable e){
			ResponseModel responseModel=  ServletExceptionUtil.exception(req, response, e);
			HttpServletResponseUtil.write(request, response, null, responseModel);
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
