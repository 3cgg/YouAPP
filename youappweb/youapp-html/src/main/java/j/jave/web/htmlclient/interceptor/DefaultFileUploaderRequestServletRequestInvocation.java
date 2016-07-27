package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.FileUploaderVO;

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
public class DefaultFileUploaderRequestServletRequestInvocation implements FileUploaderRequestServletRequestInvocation{
	
	private int currentInterceptorIndex = -1;
	
	private final HttpServletRequest httpServletRequest;

	private HttpServletResponse httpServletResponse;
	
	private Exception exception;
	
	private String unique;
	
	private FileUploaderVO fileUploaderVO ;
	
	private static List<FileUploaderRequestServletRequestInterceptor> FILE_INTERCEPTORS=new ArrayList<FileUploaderRequestServletRequestInterceptor>(8);
	
	static{
		FILE_INTERCEPTORS.add(new FileExtracterInterceptor());
		FILE_INTERCEPTORS.add(new FileTransferInterceptor());
	}
	
	public DefaultFileUploaderRequestServletRequestInvocation(ServletRequest servletRequest,ServletResponse servletResponse) {
		this.httpServletRequest=(HttpServletRequest) servletRequest;
		this.httpServletResponse=(HttpServletResponse) servletResponse;
	}
	/**
	 * {@inheritDoc}
	 */
	@Override
	public Object proceed() {
		FileUploaderRequestServletRequestInterceptor interceptor =
				FILE_INTERCEPTORS.get(++this.currentInterceptorIndex);
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
	public FileUploaderVO getFileUploaderVO() {
		return fileUploaderVO;
	}
	public void setFileUploaderVO(FileUploaderVO fileUploaderVO) {
		this.fileUploaderVO = fileUploaderVO;
	}
	
	
}
