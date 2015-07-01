/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.filedistribute.JDefaultLocalFileDistService;
import j.jave.framework.commons.filedistribute.eventdriven.JFileDistStoreEvent;
import j.jave.framework.commons.io.JFile;

import java.net.URI;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultLocalFileDistServiceImpl")
public class DefaultLocalFileDistServiceImpl implements FileDistService {

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
	public Object trigger(JFileDistStoreEvent event) {
		return defaultLocalFileDistService.trigger(event);
	}
	
	
}
