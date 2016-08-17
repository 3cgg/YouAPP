package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.filedistribute.JFileDistService;
import j.jave.kernal.filedistribute.JFileDistServicers;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.request.FileAttachedParamVO;
import j.jave.web.htmlclient.request.FileUploaderVO;
import j.jave.web.htmlclient.response.ResponseModel;

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
