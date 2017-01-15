package me.bunny.app._c._web.web.youappmvc.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.HttpContext;

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
