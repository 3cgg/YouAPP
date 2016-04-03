/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.servlet.MvcServiceServlet.JServletViewHandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author J
 */
public class JSONServletViewHandler  implements JServletViewHandler {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JSONServletViewHandler.class);
	
	private List<DataModifyHandler> dataModifyHandlers=new ArrayList<DataModifyHandler>(); 
	{
		dataModifyHandlers.add(new SimplePropertyExtendHandler());
	}
	@Override
	public void handleNavigate(HttpServletRequest request,
			HttpServletResponse response,HttpContext httpContext, Object navigate) throws Exception {
		ResponseModel responseModel=(ResponseModel)navigate;
		for(DataModifyHandler dataModifyHandler:dataModifyHandlers){
			dataModifyHandler.handle(responseModel);
		}
		HttpServletResponseUtil.write(request, response, httpContext, responseModel);
	}
	
	@Override
	public void handleServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HttpContext httpContext,
			JServiceException exception) {
		try{
			ResponseModel responseModel=ResponseModel.newMessage();
			responseModel.setData(exception.getMessage());
			HttpServletResponseUtil.write(request, response, httpContext, responseModel);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
	@Override
	public void handleExcepion(HttpServletRequest request,
			HttpServletResponse response, HttpContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(JServiceException.class.isInstance(exp)){
				handleServiceExcepion(request, response, httpContext, (JServiceException) exp);
			}
			else{
				ResponseModel responseModel=ResponseModel.newError();
				responseModel.setData(exception.getMessage());
				HttpServletResponseUtil.write(request, response, httpContext, responseModel);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
