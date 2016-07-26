package j.jave.web.htmlclient.interceptor;

import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.web.htmlclient.request.FileUploaderVO;
import j.jave.web.htmlclient.servlet.ServletUtil;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;


/**
 * @author JIAZJ
 *
 */
public class FileExtracterInterceptor implements FileUploaderRequestServletRequestInterceptor {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(FileExtracterInterceptor.class);
	
	@Override
	public Object intercept(FileUploaderRequestServletRequestInvocation servletRequestInvocation) {
        
		Map<String, Object> entries=ServletUtil.doWithRequestParameterWithFileAttached(servletRequestInvocation.getHttpServletRequest());
		FileUploaderVO fileUploaderVO=new FileUploaderVO();
		
		for (Iterator<Entry<String, Object>> iterator = entries.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, Object> entry = iterator.next();
			Object val=entry.getValue();
			if(val!=null&&JFile.class.isInstance(val)){
				fileUploaderVO.setFile((JFile) val);
			}
		}
		servletRequestInvocation.setFileUploaderVO(fileUploaderVO);
        return servletRequestInvocation.proceed();

	}
	
	
}
