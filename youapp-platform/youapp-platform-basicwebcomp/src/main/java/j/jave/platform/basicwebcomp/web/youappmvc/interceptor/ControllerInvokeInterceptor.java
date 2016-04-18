package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerExecutor;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.JSONServletViewHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ControllerInvokeInterceptor implements ServletRequestInterceptor {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ControllerInvokeInterceptor.class);
	
	private JServletViewHandler servletViewHandler=new JSONServletViewHandler();
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {
		
		HttpServletRequest req=servletRequestInvocation.getHttpServletRequest();
		HttpServletResponse resp=servletRequestInvocation.getHttpServletResponse();

		HttpContext httpContext=HttpContextHolder.get().initHttp(req, resp);
		servletRequestInvocation.setHttpContext(httpContext);
		try{
			
			String target=servletRequestInvocation.getMappingPath();
			httpContext.setTargetPath(target);
			httpContext.setUnique(servletRequestInvocation.getUnique());
			
			Object navigate=ControllerExecutor.newSingleExecutor().execute(httpContext);
			if(LOGGER.isDebugEnabled()){
				LOGGER.debug("the response of "+req.getRequestURL()+"[DispathType:"+req.getDispatcherType().name()+"] is OK!");
			}
			// if response for download.
			if(JFile.class.isInstance(navigate)){
				JFile file=(JFile)navigate;
				return file;
			}
			else{
				return servletViewHandler.handleNavigate(req, resp,httpContext, navigate);
			}
		}
		catch(JServiceException e){
			LOGGER.error(e.getMessage(),e);
			return servletViewHandler.handleServiceExcepion(req, resp, httpContext, e);
		}
		catch(Exception e){
			LOGGER.error(e.getMessage(),e);
			return servletViewHandler.handleExcepion(req, resp,httpContext,  e);
		}
		
	}
	
	
	
}
