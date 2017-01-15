package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.FileAttachedParamVO;
import j.jave.web.htmlclient.request.FileUploaderVO;
import j.jave.web.htmlclient.response.ResponseModel;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.filedistribute.JFileDistService;
import me.bunny.kernel.filedistribute.JFileDistServicers;

import java.net.URI;


/**
 * @author JIAZJ
 *
 */
public class FileTransferInterceptor implements FileUploaderRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(FileTransferInterceptor.class);
	
	JFileDistService fileDistService= JFileDistServicers.newSingleLocalFileDistService("d:/file-store");
	
	@Override
	public Object intercept(FileUploaderRequestServletRequestInvocation servletRequestInvocation) {
        
		FileUploaderVO fileUploaderVO=servletRequestInvocation.getFileUploaderVO();
		try {
			URI uri=fileDistService.distribute(fileUploaderVO.getFile());
			FileAttachedParamVO fileAttachedParamVO=new FileAttachedParamVO();
			fileAttachedParamVO.setFileUrl(uri.toString());
			fileUploaderVO.setFileAttachedParamVO(fileAttachedParamVO);
		} catch (JServiceException e) {
			LOGGER.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
		ResponseModel responseModel=ResponseModel.newSuccess();
		responseModel.setData(fileUploaderVO.getFileAttachedParamVO());
		return responseModel;
		
	}
	
	
}
