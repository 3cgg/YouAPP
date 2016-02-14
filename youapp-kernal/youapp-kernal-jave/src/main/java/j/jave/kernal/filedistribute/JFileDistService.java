/**
 * 
 */
package j.jave.kernal.filedistribute;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.filedistribute.eventdriven.JFileDistStoreListener;
import j.jave.kernal.jave.io.JFile;
import j.jave.kernal.jave.service.JService;

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
