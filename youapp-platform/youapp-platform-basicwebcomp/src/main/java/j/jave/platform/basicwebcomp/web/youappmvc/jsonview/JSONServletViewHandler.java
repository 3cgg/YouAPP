/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.json.JJSON;
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
		String out=JJSON.get().formatObject(responseModel);
		String callBackMethod=httpContext.getParameter("callback");
		String callBackForJsonp=callBackMethod+"("+out+")";
		response.getOutputStream().write(callBackForJsonp.getBytes("utf-8"));
	}
	
	@Override
	public void handleServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HttpContext httpContext,
			JServiceException exception) {
		try{
			ResponseModel mobileResult=ResponseModel.newMessage();
			mobileResult.setData(exception.getMessage());
			String out=JJSON.get().formatObject(mobileResult);
			response.getOutputStream().write(out.getBytes("utf-8"));
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
				ResponseModel mobileResult=ResponseModel.newError();
				mobileResult.setData(exception.getMessage());
				String out=JJSON.get().formatObject(mobileResult);
				response.getOutputStream().write(out.getBytes("utf-8"));
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
