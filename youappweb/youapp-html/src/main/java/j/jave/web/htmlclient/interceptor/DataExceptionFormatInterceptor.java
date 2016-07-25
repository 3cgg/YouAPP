package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.response.ResponseModel;


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
