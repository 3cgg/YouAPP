/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.io.JFile;
import j.jave.framework.servicehub.exception.JServiceException;
import j.jave.framework.servicehub.filedistribute.JDefaultLocalFileDistService;
import j.jave.framework.servicehub.filedistribute.JFileDisStoreEvent;

import java.net.URI;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultLocalFileDistServiceImpl")
public class DefaultLocalFileDistServiceImpl implements FileDisService {

	private JDefaultLocalFileDistService defaultLocalFileDistService;
	
	@Override
	public URI distribute(JFile file) throws JServiceException {
		return defaultLocalFileDistService.distribute(file);
	}

	/**
	 * @param defaultLocalFileDistService the defaultLocalFileDistService to set
	 */
	public void setDefaultLocalFileDistService(
			JDefaultLocalFileDistService defaultLocalFileDistService) {
		this.defaultLocalFileDistService = defaultLocalFileDistService;
	}
	
	@Override
	public Object trigger(JFileDisStoreEvent event) {
		return defaultLocalFileDistService.trigger(event);
	}
	
	
}
