package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.kernal.jave.json.JJSON;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class FilterExceptionUtil {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(FilterExceptionUtil.class);
	
	public static void exception(ServletRequest request, ServletResponse response,Exception exception){
		try{
			ResponseModel responseModel=FilterResponse.newError().setData(exception.getMessage());
			response.getOutputStream().write(JJSON.get().formatObject(responseModel).getBytes("utf-8"));
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
