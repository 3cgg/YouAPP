package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.platform.webcomp.web.youappmvc.HttpContextHolder;
import j.jave.platform.webcomp.web.youappmvc.ServletHttpContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @author J
 */
public class HttpContextExtracterInterceptor implements ServletRequestInterceptor{

	private static final JLogger LOGGER=JLoggerFactory.getLogger(HttpContextExtracterInterceptor.class);
	
	@Override
	public Object intercept(ServletRequestInvocation servletRequestInvocation) {

		HttpServletRequest req=(HttpServletRequest) servletRequestInvocation.getRequestContext().getSource();
		HttpServletResponse response=(HttpServletResponse) servletRequestInvocation.getResponseContext().getSource();
		try{
			ServletHttpContext httpContext=new ServletHttpContext(req,response);
			httpContext.initHttpClient(req, response);
			HttpContextHolder.set(httpContext);
			servletRequestInvocation.setHttpContext(httpContext);
			return servletRequestInvocation.proceed();
		}catch(Throwable e){
			LOGGER.error(e.getMessage(), e); 
			return e;
		}
	}
	
	
}
