/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.filedistribute.subhub;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.filedistribute.JDefaultLocalFileDistService;
import j.jave.kernal.filedistribute.JFileDistService;
import j.jave.kernal.filedistribute.JFileDistServiceAware;
import j.jave.kernal.filedistribute.eventdriven.JFileDistStoreEvent;
import j.jave.kernal.jave.io.JFile;

import java.net.URI;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultLocalFileDistServiceImpl")
public class DefaultLocalFileDistServiceImpl implements FileDistService ,JFileDistServiceAware{

	private JDefaultLocalFileDistService defaultLocalFileDistService;
	
	@Override
	public URI distribute(JFile file) throws JServiceException {
		return defaultLocalFileDistService.distribute(file);
	}
	
	@Override
	public void setFileDistService(JFileDistService fileDistService) {
		this.defaultLocalFileDistService = (JDefaultLocalFileDistService) fileDistService;
	}
	
	@Override
	public JFileDistService getFileDistService() {
		return this.defaultLocalFileDistService;
	}
	
	@Override
	public Object trigger(JFileDistStoreEvent event) {
		return defaultLocalFileDistService.trigger(event);
	}
	
	
}
