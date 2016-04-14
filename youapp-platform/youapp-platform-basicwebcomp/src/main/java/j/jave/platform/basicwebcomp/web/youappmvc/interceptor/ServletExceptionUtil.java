package j.jave.platform.basicwebcomp.web.youappmvc.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class ServletExceptionUtil {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(ServletExceptionUtil.class);
	
	public static ResponseModel exception(ServletRequest request, ServletResponse response,Exception exception){
		try{
			ResponseModel responseModel=ResponseModel.newError().setData(exception.getMessage());
//			HttpServletResponseUtil.write((HttpServletRequest)request, (HttpServletResponse)response, HttpContextHolder.get(), responseModel);
			return responseModel;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			return exception(request, response, e);
		}
	}
	
}
