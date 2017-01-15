/**
 * 
 */
package me.bunny.kernel.filedistribute;

import java.net.URI;

import me.bunny.kernel._c.io.JFile;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.filedistribute.eventdriven.JFileDistStoreListener;

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
