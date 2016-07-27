package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.request.FileUploaderVO;


/**
 * @author JIAZJ
 *
 */
public class FileTransferInterceptor implements FileUploaderRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(FileTransferInterceptor.class);
	
	@Override
	public Object intercept(FileUploaderRequestServletRequestInvocation servletRequestInvocation) {
        
		FileUploaderVO fileUploaderVO=servletRequestInvocation.getFileUploaderVO();
		
		return new Object();
		
	}
	
	
}
