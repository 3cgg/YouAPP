package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.response.ResponseModel;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;


/**
 * @author JIAZJ
 *
 */
public class DataExceptionFormatInterceptor implements DataRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(DataExceptionFormatInterceptor.class);
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		try{
			return servletRequestInvocation.proceed();
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e);
			ResponseModel responseModel=ResponseModel.newError();
			responseModel.setData(e.getMessage());
			return responseModel;
		}
	}
	
	
}
