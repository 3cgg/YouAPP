package j.jave.platform.webcomp.web.youappmvc.interceptor;

import j.jave.platform.webcomp.web.youappmvc.HttpContext;
import j.jave.platform.webcomp.web.youappmvc.RequestContext;
import j.jave.platform.webcomp.web.youappmvc.ResponseContext;
import j.jave.platform.webcomp.web.youappmvc.ServletRequestContext;
import j.jave.platform.webcomp.web.youappmvc.ServletResponseContext;

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
	
	private HttpContext httpContext;
	
	private final HttpServletRequest httpServletRequest;

	private HttpServletResponse httpServletResponse;
	
	private RequestContext requestContext;
	
	private ResponseContext responseContext;
	
	private Exception exception;
	
	private String unique;
	
	private String mappingPath;
	
	private static List<ServletRequestInterceptor> MODEL_INTERCEPTORS=new ArrayList<ServletRequestInterceptor>(8);
	
	static{
//		MODEL_INTERCEPTORS.add(new FormTokenValidatorInterceptor());
		MODEL_INTERCEPTORS.add(new HttpContextExtracterInterceptor());
		MODEL_INTERCEPTORS.add(new MultiVersionCheckInterceptor());
		MODEL_INTERCEPTORS.add(new TicketValidationInterceptor());
		MODEL_INTERCEPTORS.add(new AuthenticationInterceptor());
		MODEL_INTERCEPTORS.add(new ResourceAccessInterceptor());
		MODEL_INTERCEPTORS.add(new ValidPathInterceptor());
		MODEL_INTERCEPTORS.add(new MemoryHTMLInterceptor());
		MODEL_INTERCEPTORS.add(new ControllerInvokeInterceptor());
	}
	
	public DefaultServletRequestInvocation(ServletRequest servletRequest,ServletResponse servletResponse) {
		this.httpServletRequest=(HttpServletRequest) servletRequest;
		this.httpServletResponse=(HttpServletResponse) servletResponse;
		this.requestContext=new ServletRequestContext(httpServletRequest);
		this.responseContext=new ServletResponseContext(httpServletResponse);
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object proceed() throws Throwable{
		
		if (this.currentInterceptorIndex == MODEL_INTERCEPTORS.size() - 1) {
			return new Object();
		}
		
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
	
	@Override
	public HttpContext getHttpContext() {
		return this.httpContext;
	}
	@Override
	public void setHttpContext(HttpContext httpContext) {
		this.httpContext=httpContext;
	}
	
	public void setUnique(String unique) {
		this.unique = unique;
	}
	
	public String getUnique() {
		return unique;
	}
	public String getMappingPath() {
		return mappingPath;
	}
	public void setMappingPath(String mappingPath) {
		this.mappingPath = mappingPath;
	}
	@Override
	public RequestContext getRequestContext() {
		return this.requestContext;
	}
	@Override
	public ResponseContext getResponseContext() {
		return this.responseContext;
	}
	
}
