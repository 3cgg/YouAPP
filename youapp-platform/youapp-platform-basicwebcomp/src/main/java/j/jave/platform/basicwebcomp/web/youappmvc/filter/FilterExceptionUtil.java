package j.jave.platform.basicwebcomp.web.youappmvc.filter;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.basicwebcomp.web.youappmvc.jsonview.HttpServletResponseUtil;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class FilterExceptionUtil {

	private static final JLogger LOGGER=JLoggerFactory.getLogger(FilterExceptionUtil.class);
	
	public static void exception(ServletRequest request, ServletResponse response,Exception exception){
		try{
			ResponseModel responseModel=ResponseModel.newError().setData(exception.getMessage());
			HttpServletResponseUtil.write((HttpServletRequest)request, (HttpServletResponse)response, HttpContextHolder.get(), responseModel);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
		}
	}
	
}
