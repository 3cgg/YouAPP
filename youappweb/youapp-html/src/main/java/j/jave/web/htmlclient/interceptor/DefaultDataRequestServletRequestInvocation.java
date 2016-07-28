package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.plugins.jquerydatatable.PageableInterceptor;
import j.jave.web.htmlclient.request.RequestVO;

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
public class DefaultDataRequestServletRequestInvocation implements DataRequestServletRequestInvocation{
	
	private int currentInterceptorIndex = -1;
	
	private final HttpServletRequest httpServletRequest;

	private HttpServletResponse httpServletResponse;
	
	private Exception exception;
	
	private String unique;
	
	private RequestVO requestVO;
	
	private static List<DataRequestServletRequestInterceptor> MODEL_INTERCEPTORS=new ArrayList<DataRequestServletRequestInterceptor>(8);
	
	static{
		MODEL_INTERCEPTORS.add(new DataExceptionFormatInterceptor());
		MODEL_INTERCEPTORS.add(new DataExtracterInterceptor());
		MODEL_INTERCEPTORS.add(new PageableInterceptor());
		MODEL_INTERCEPTORS.add(new DataQueryInterceptor());
		MODEL_INTERCEPTORS.add(new DataTempInterceptor());
	}
	
	public DefaultDataRequestServletRequestInvocation(ServletRequest servletRequest,ServletResponse servletResponse) {
		this.httpServletRequest=(HttpServletRequest) servletRequest;
		this.httpServletResponse=(HttpServletResponse) servletResponse;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object proceed() {
		DataRequestServletRequestInterceptor interceptor =
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
	public RequestVO getRequestVO() {
		return requestVO;
	}
	public void setRequestVO(RequestVO requestVO) {
		this.requestVO = requestVO;
	}
	
}
