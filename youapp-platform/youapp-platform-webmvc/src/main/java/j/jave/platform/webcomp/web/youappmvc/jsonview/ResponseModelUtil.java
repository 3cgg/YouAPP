package j.jave.platform.webcomp.web.youappmvc.jsonview;

import j.jave.platform.webcomp.WebCompProperties;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.utils.JStringUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

class ResponseModelUtil {
	
	public static abstract class Interceptor{
		public abstract Object intercept(ServletRequest request,ServletResponse response,HttpContext httpContext,ResponseModel responseModel);
	}
	
	public static class JSONPInterceptor extends Interceptor{
		private static final String JSONP_CALLBACK=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_JSONP_CALLBACK, "callback");
		
		private static final boolean JSONP_ONOFF=JConfiguration.get().getBoolean(WebCompProperties.YOUAPPMVC_JSONP_ONOFF, true);
		
		public Object intercept(ServletRequest request,ServletResponse response,HttpContext httpContext,ResponseModel responseModel){
			HttpServletRequest httpServletRequest=(HttpServletRequest) request;
			
			if(JSONP_ONOFF){
				String callBackMethod=null;
				if(httpContext!=null){
					callBackMethod=httpContext.getParameter(JSONP_CALLBACK);
				}
				if(JStringUtils.isNullOrEmpty(callBackMethod)){
					callBackMethod=httpServletRequest.getParameter(JSONP_CALLBACK);
				}
				if(JStringUtils.isNotNullOrEmpty(callBackMethod)){
					//jsonp found
					String out=JJSON.get().formatObject(responseModel);
					String callBackForJsonp=callBackMethod+"("+out+")";
					return callBackForJsonp;
				}
			}
			return JJSON.get().formatObject(responseModel);
		}
		
	}
	
	private static final JSONPInterceptor JSONP_INTERCEPTOR=new JSONPInterceptor();
	
	/**
	 * the final string which used to be send back to client.
	 * @param request
	 * @param response
	 * @param httpContext
	 * @param responseModel
	 * @return
	 */
	public static String intercept(ServletRequest request,ServletResponse response,HttpContext httpContext,ResponseModel responseModel){
		return String.valueOf(JSONP_INTERCEPTOR.intercept(request, response, httpContext, responseModel));
	}
	
	
}
