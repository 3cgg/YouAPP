package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.SyncHtmlResponseService;


/**
 * @author JIAZJ
 *
 */
public class HtmlExceptionFormatInterceptor implements HtmlRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(HtmlExceptionFormatInterceptor.class);
	
	private SyncHtmlResponseService syncHtmlResponseService=SyncHtmlResponseService.get();
	
	@Override
	public Object intercept(HtmlRequestServletRequestInvocation servletRequestInvocation) {
		try{
			return servletRequestInvocation.proceed();
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e);
			return syncHtmlResponseService.miniError(e.getMessage());
		}
	}
	
	
}
