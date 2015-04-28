/**
 * 
 */
package j.jave.framework.servicehub.filedistribute;

import j.jave.framework.io.JFile;
import j.jave.framework.servicehub.JService;
import j.jave.framework.servicehub.JServiceException;

import java.net.URI;

/**
 * distribute the file to remote or local location. 
 * @author J
 */
public interface JFileDisService extends JService{

	/**
	 * return A URI target to the file. 
	 * @param file
	 * @return
	 */
	public URI distribute(JFile file) throws JServiceException;
	
}
