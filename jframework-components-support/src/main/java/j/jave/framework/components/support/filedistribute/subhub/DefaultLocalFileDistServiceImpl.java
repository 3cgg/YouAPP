/**
 * 
 */
package j.jave.framework.components.support.filedistribute.subhub;

import j.jave.framework.filedistribute.JDefaultLocalFileDistService;
import j.jave.framework.io.JFile;
import j.jave.framework.servicehub.JServiceException;

import java.net.URI;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultLocalFileDistServiceImpl")
public class DefaultLocalFileDistServiceImpl implements FileDisService {

	private JDefaultLocalFileDistService defaultLocalFileDistService;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.filedistribute.JFileDisService#distribute(j.jave.framework.io.JFile)
	 */
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
}
