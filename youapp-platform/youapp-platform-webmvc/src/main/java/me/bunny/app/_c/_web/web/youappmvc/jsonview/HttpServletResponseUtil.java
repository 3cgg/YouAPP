package me.bunny.app._c._web.web.youappmvc.jsonview;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import me.bunny.app._c._web.web.model.ResponseModel;
import me.bunny.app._c._web.web.youappmvc.HttpContext;

/**
 *  the unified writing stream for servlet/filter.
 * @author JIAZJ
 *
 */
public class HttpServletResponseUtil {

	public static final void write(HttpServletRequest request,
			HttpServletResponse response,HttpContext httpContext, Object data){
		Object responseModel=data;
		if(ResponseModel.class.isInstance(data)){
			responseModel=ResponseModelUtil.intercept(request, response, httpContext, (ResponseModel) data);
		}
		
		try {
			writeBytesDirectly(request, response, String.valueOf(responseModel).getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} 
	}
	
	
	public static final void writeBytesDirectly(HttpServletRequest request,
			HttpServletResponse response,byte[] bytes){
		OutputStream outputStream=null;
		try {
			outputStream=response.getOutputStream();
			response.getOutputStream().write(bytes);
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			if(outputStream!=null){
				try {
					outputStream.flush();
					outputStream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
