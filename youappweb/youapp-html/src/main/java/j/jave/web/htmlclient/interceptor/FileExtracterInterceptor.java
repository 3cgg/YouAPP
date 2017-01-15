package j.jave.web.htmlclient.interceptor;

import j.jave.web.htmlclient.request.FileUploaderVO;
import j.jave.web.htmlclient.servlet.ServletUtil;
import me.bunny.kernel.jave.io.JFile;
import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;

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
			else{
				fileUploaderVO.getFileAttachedParamVO().put(entry.getKey(), entry.getValue());
			}
		}
		servletRequestInvocation.setFileUploaderVO(fileUploaderVO);
        return servletRequestInvocation.proceed();

	}
	
	
}
