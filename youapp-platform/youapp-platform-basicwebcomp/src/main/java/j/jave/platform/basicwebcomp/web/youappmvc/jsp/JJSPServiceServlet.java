/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.jsp;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.web.youappmvc.model.JHttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.multi.platform.servlet.JServiceServlet;
import j.jave.platform.basicwebcomp.web.youappmvc.utils.JHttpUtils;

import java.io.IOException;
import java.nio.charset.Charset;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * JSP Service Servlet
 * @author J
 */
public class JJSPServiceServlet  extends JServiceServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JJSPServiceServlet.class);
	
	@Override
	protected void handlerNavigate(HttpServletRequest request,
			HttpServletResponse response,JHttpContext httpContext, Object navigate) throws Exception {
		if(String.class.isInstance(navigate)){  // its forward to JSP. 
			String expectJsp=(String)navigate;
			if(expectJsp.endsWith(".jsp")){
				JHttpUtils.setHttpContext(request, httpContext);
				request.getRequestDispatcher(expectJsp).forward(request, response);
			}
			else{
				response.getOutputStream().write(expectJsp.getBytes(Charset.forName("utf-8")));
			}
		}
	}
	
	@Override
	protected void handlerServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			JServiceException exception) {
		try{
			request.setAttribute("message", exception.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/warning.jsp").forward(request, response); 
		}catch(IOException e){
			LOGGER.error(e.getMessage(),e);
		}
		catch(ServletException e){
			LOGGER.error(e.getMessage(),e);
		}
	}
	
	@Override
	protected void handlerExcepion(HttpServletRequest request,
			HttpServletResponse response, JHttpContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(!JServiceException.class.isInstance(exp)){
				LOGGER.error(exception.getMessage(), exception); 
			}
			
			request.setAttribute("message", exp.getMessage());
			String expectJsp="/WEB-INF/jsp/error.jsp";
			if(JServiceException.class.isInstance(exp)){
				expectJsp="/WEB-INF/jsp/warning.jsp";
			}
			request.getRequestDispatcher(expectJsp).forward(request, response); 
		}catch(Exception e){
			LOGGER.error(e.getMessage(),e);
		}
	}
	
}
