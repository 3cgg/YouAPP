/**
 * 
 */
package j.jave.framework.commons.filedistribute;

import j.jave.framework.commons.eventdriven.exception.JServiceException;
import j.jave.framework.commons.filedistribute.eventdriven.JFileDistStoreListener;
import j.jave.framework.commons.io.JFile;
import j.jave.framework.commons.service.JService;

import java.net.URI;

/**
 * distribute the file to remote or local location. 
 * @author J
 */
public interface JFileDistService extends JService , JFileDistStoreListener{

	/**
	 * return A URI target to the file. 
	 * @param file
	 * @return
	 */
	public URI distribute(JFile file) throws JServiceException;
	
}
