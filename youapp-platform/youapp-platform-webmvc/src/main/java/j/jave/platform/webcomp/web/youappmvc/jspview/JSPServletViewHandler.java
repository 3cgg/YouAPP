/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.jspview;


/**
 * JSP Service Servlet
 * @author J
 */
@Deprecated
public class JSPServletViewHandler   {
//
//	private static final Logger LOGGER=LoggerFactory.getLogger(JSPServletViewHandler.class);
//	
//	@Override
//	public void handleNavigate(HttpServletRequest request,
//			HttpServletResponse response,HttpContext httpContext, Object navigate) throws Exception {
//		if(String.class.isInstance(navigate)){  // its forward to JSP. 
//			String expectJsp=(String)navigate;
//			if(expectJsp.endsWith(".jsp")){
//				YouAppMvcUtils.setHttpContext(request, httpContext);
//				request.getRequestDispatcher(expectJsp).forward(request, response);
//			}
//			else{
//				response.getOutputStream().write(expectJsp.getBytes(Charset.forName("utf-8")));
//			}
//		}
//	}
//	
//	@Override
//	public void handleServiceExcepion(HttpServletRequest request,
//			HttpServletResponse response, HttpContext httpContext,
//			JServiceException exception) {
//		try{
//			request.setAttribute("message", exception.getMessage());
//			request.getRequestDispatcher("/WEB-INF/jsp/warning.jsp").forward(request, response); 
//		}catch(IOException e){
//			LOGGER.error(e.getMessage(),e);
//		}
//		catch(ServletException e){
//			LOGGER.error(e.getMessage(),e);
//		}
//	}
//	
//	@Override
//	public void handleExcepion(HttpServletRequest request,
//			HttpServletResponse response, HttpContext httpContext,
//			Exception exception) {
//		try{
//			Throwable exp=exception;
//			while(exp.getCause()!=null){
//				exp=exp.getCause();
//			}
//			
//			if(!JServiceException.class.isInstance(exp)){
//				LOGGER.error(exception.getMessage(), exception); 
//			}
//			
//			request.setAttribute("message", exp.getMessage());
//			String expectJsp="/WEB-INF/jsp/error.jsp";
//			if(JServiceException.class.isInstance(exp)){
//				expectJsp="/WEB-INF/jsp/warning.jsp";
//			}
//			request.getRequestDispatcher(expectJsp).forward(request, response); 
//		}catch(Exception e){
//			LOGGER.error(e.getMessage(),e);
//		}
//	}
//	
}
