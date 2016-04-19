/**
 * 
 */
package j.jave.platform.basicwebcomp.web.youappmvc.jsonview;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.databind.proext.JPropertyExtendBinder;
import j.jave.kernal.jave.support.databind.proext.JSimplePropertyExtendBinder;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContext;
import j.jave.platform.basicwebcomp.web.youappmvc.interceptor.ServletExceptionUtil;
import j.jave.platform.basicwebcomp.web.youappmvc.interceptor.JServletViewHandler;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author J
 */
public class JSONServletViewHandler  implements JServletViewHandler {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(JSONServletViewHandler.class);
	
	private List<JPropertyExtendBinder> propertyExtendBinders=new ArrayList<JPropertyExtendBinder>(); 
	{
		propertyExtendBinders.add(new JSimplePropertyExtendBinder());
	}
	@Override
	public Object handleNavigate(HttpServletRequest request,
			HttpServletResponse response,HttpContext httpContext, Object navigate) throws Exception {
		ResponseModel responseModel=(ResponseModel)navigate;
		for(JPropertyExtendBinder propertyExtendBinder:propertyExtendBinders){
			propertyExtendBinder.bind(responseModel.getData());
		}
		return responseModel;
	}
	
	@Override
	public Object handleServiceExcepion(HttpServletRequest request,
			HttpServletResponse response, HttpContext httpContext,
			JServiceException exception) {
		try{
			ResponseModel responseModel=ResponseModel.newMessage();
			responseModel.setData(exception.getMessage());
			return responseModel;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return ServletExceptionUtil.exception(request, response, e); 
		}
	}
	
	@Override
	public Object handleExcepion(HttpServletRequest request,
			HttpServletResponse response, HttpContext httpContext,
			Exception exception) {
		try{
			Throwable exp=exception;
			while(exp.getCause()!=null){
				exp=exp.getCause();
			}
			
			if(JServiceException.class.isInstance(exp)){
				return handleServiceExcepion(request, response, httpContext, (JServiceException) exp);
			}
			else{
				ResponseModel responseModel=ResponseModel.newError();
				responseModel.setData(exp.getMessage());
				return responseModel;
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return ServletExceptionUtil.exception(request, response, e);
		}
	}
	
}
