package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.FileUploaderVO;


/**
 * @author J
 */
public interface FileUploaderRequestServletRequestInvocation extends ServletRequestInvocation {
	
	FileUploaderVO getFileUploaderVO();
	
	void setFileUploaderVO(FileUploaderVO fileUploaderVO );

}
