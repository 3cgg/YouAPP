/**
 * 
 */
package j.jave.framework.components.web.jsp;

import j.jave.framework.components.web.action.HTTPContext;
import j.jave.framework.components.web.multi.platform.servlet.JServiceServlet;
import j.jave.framework.components.web.utils.HTTPUtils;
import j.jave.framework.servicehub.exception.JServiceException;

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
			HttpServletResponse response,HTTPContext httpContext, Object navigate) throws Exception {
		if(String.class.isInstance(navigate)){  // its forward to JSP. 
			String expectJsp=(String)navigate;
			if(expectJsp.endsWith(".jsp")){
				HTTPUtils.setHttpContext(request, httpContext);
				request.getRequestDispatcher(expectJsp).forward(request, response);
			}
			else{
				response.getOutputStream().write(expectJsp.getBytes(Charset.forName("utf-8")));
			}
		}
	}
	
	@Override
	protected void handlerServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HTTPContext httpContext,
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
			HttpServletResponse response, HTTPContext httpContext,
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
