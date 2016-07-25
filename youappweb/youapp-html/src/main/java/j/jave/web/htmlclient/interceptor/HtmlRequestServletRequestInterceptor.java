package j.jave.web.htmlclient.interceptor;


/**
 * 
 * @author J
 *
 */
public interface HtmlRequestServletRequestInterceptor extends ServletRequestInterceptor {

	public abstract Object intercept(HtmlRequestServletRequestInvocation servletRequestInvocation);
	
}
