package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.RequestParamNames;
import j.jave.web.htmlclient.request.RequestVO;
import me.bunny.kernel.jave.json.JJSON;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;


/**
 * @author JIAZJ
 *
 */
public class DataExtracterInterceptor implements DataRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(DataExtracterInterceptor.class);
	
	@Override
	public Object intercept(DataRequestServletRequestInvocation servletRequestInvocation) {
		
		String requestData=servletRequestInvocation.getHttpServletRequest().getParameter(RequestParamNames.REQUEST_DATA);
        
        if(LOGGER.isDebugEnabled()){
        	LOGGER.debug("the request data (data)-> "+requestData);
        }
        if(requestData!=null&&requestData.length()>0){
        	RequestVO requestVO=JJSON.get().parse(requestData, RequestVO.class);
        	servletRequestInvocation.setRequestVO(requestVO);
        }
        else{
        	throw new RuntimeException("request data (data) is missing.");
        }
        return servletRequestInvocation.proceed();

	}
	
	
}
