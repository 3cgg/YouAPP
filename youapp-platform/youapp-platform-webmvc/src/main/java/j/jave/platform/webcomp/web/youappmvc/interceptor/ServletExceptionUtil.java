package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServletExceptionUtil {
	
	public static ResponseModel exception(HttpContext httpContext,Throwable exception){
		ResponseModel responseModel=ResponseModel.newError().setData(exception.getMessage());
		return responseModel;
	}
	
	public static ResponseModel exception(HttpServletRequest req, HttpServletResponse resp,Throwable exception){
		ResponseModel responseModel=ResponseModel.newError().setData(exception.getMessage());
		return responseModel;
	}
}
