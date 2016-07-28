/**
 * 
 */
package j.jave.platform.webcomp.web.youappmvc.jsonview;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.support.databind.proext.JPropertyExtendBinder;
import j.jave.kernal.jave.support.databind.proext.JSimplePropertyExtendBinder;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.RequestContext;
import j.jave.platform.webcomp.web.youappmvc.ResponseContext;
import j.jave.platform.webcomp.web.youappmvc.interceptor.JServletViewHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 */
public class JSONServletViewHandler  implements JServletViewHandler {
	
	private List<JPropertyExtendBinder> propertyExtendBinders=new ArrayList<JPropertyExtendBinder>(); 
	{
		propertyExtendBinders.add(new JSimplePropertyExtendBinder());
	}
	@Override
	public Object handleNavigate(RequestContext request,
			ResponseContext response,HttpContext httpContext, Object navigate) throws Exception {
		ResponseModel responseModel=(ResponseModel)navigate;
		for(JPropertyExtendBinder propertyExtendBinder:propertyExtendBinders){
			propertyExtendBinder.bind(responseModel.getData());
		}
		return responseModel;
	}
	
	@Override
	public Object handleServiceExcepion(RequestContext request,
			ResponseContext response, HttpContext httpContext,
			JServiceException exception) {
		ResponseModel responseModel=ResponseModel.newMessage();
		responseModel.setData(exception.getMessage());
		return responseModel;
	}
	
	@Override
	public Object handleExcepion(RequestContext request,
			ResponseContext response, HttpContext httpContext,
			Exception exception) {
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
	}
	
}
