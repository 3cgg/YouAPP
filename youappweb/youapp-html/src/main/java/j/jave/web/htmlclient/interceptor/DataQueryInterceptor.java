package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.RequestVO;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;


/**
 * @author JIAZJ
 *
 */
public class DataQueryInterceptor implements DataRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(DataQueryInterceptor.class);
	
	private DataQueryService dataQueryService=new WithProtocolRemoteDataQueryService();
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		
		RequestVO requestVO=servletRequestInvocation.getRequestVO();
		try{
			return dataQueryService.query(requestVO);
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}
	
	
}
