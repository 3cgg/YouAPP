package j.jave.web.htmlclient.interceptor;


/**
 * 
 * @author J
 *
 */
public interface FileUploaderRequestServletRequestInterceptor extends ServletRequestInterceptor {

	public abstract Object intercept(FileUploaderRequestServletRequestInvocation servletRequestInvocation);
	
}
