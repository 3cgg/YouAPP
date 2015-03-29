/**
 * 
 */
package j.jave.framework.components.views.web;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.views.HTTPContext;
import j.jave.framework.components.views.HTTPUtils;
import j.jave.framework.components.views.JServiceServlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Administrator
 *
 */
public class JWebServiceServlet  extends JServiceServlet {

	private static final Logger LOGGER=LoggerFactory.getLogger(JWebServiceServlet.class);
	
	@Override
	protected void handlerNavigate(HttpServletRequest request,
			HttpServletResponse response,HTTPContext httpContext, Object navigate) throws Exception {
		if(String.class.isInstance(navigate)){  // its forward to JSP. 
			String expectJsp=(String)navigate;
			if(expectJsp.endsWith(".jsp")){
				HTTPUtils.setHttpContext(request, httpContext);
				request.getRequestDispatcher(expectJsp).forward(request, response);
			}
		}
	}
	
	@Override
	protected void handlerServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HTTPContext httpContext,
			ServiceException exception) {
		try{
			request.setAttribute("message", exception.getMessage());
			request.getRequestDispatcher("/WEB-INF/jsp/warning.jsp").forward(request, response); 
		}catch(IOException e){
			
		}
		catch(ServletException e){
			
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
			
			if(!ServiceException.class.isInstance(exp)){
				LOGGER.error(exception.getMessage(), exception); 
			}
			
			request.setAttribute("message", exp.getMessage());
			String expectJsp="/WEB-INF/jsp/error.jsp";
			if(ServiceException.class.isInstance(exp)){
				expectJsp="/WEB-INF/jsp/warning.jsp";
			}
			request.getRequestDispatcher(expectJsp).forward(request, response); 
		}catch(Exception e){
			
		}
	}
	
}
