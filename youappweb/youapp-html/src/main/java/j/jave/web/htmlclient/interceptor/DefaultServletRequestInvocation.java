package j.jave.web.htmlclient.interceptor;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 
 * @author J
 *
 */
public class DefaultServletRequestInvocation implements ServletRequestInvocation{
	
	private int currentInterceptorIndex = -1;
	
	private final HttpServletRequest httpServletRequest;

	private HttpServletResponse httpServletResponse;
	
	private Exception exception;
	
	private String unique;
	
	private static List<ServletRequestInterceptor> MODEL_INTERCEPTORS=new ArrayList<ServletRequestInterceptor>(8);
	
	static{
//		MODEL_INTERCEPTORS.add(new FormTokenValidatorInterceptor());
//		MODEL_INTERCEPTORS.add(new MultiVersionCheckInterceptor());
	}
	
	public DefaultServletRequestInvocation(ServletRequest servletRequest,ServletResponse servletResponse) {
		this.httpServletRequest=(HttpServletRequest) servletRequest;
		this.httpServletResponse=(HttpServletResponse) servletResponse;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object proceed() {
		ServletRequestInterceptor interceptor =
				MODEL_INTERCEPTORS.get(++this.currentInterceptorIndex);
		return interceptor.intercept(this);
	}

	public Exception getException() {
		return exception;
	}

	public void setException(Exception exception) {
		this.exception = exception;
	}

	public HttpServletRequest getHttpServletRequest() {
		return httpServletRequest;
	}

	public HttpServletResponse getHttpServletResponse() {
		return httpServletResponse;
	}
	
	public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
		this.httpServletResponse = httpServletResponse;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	public String getUnique() {
		return unique;
	}
	
}
