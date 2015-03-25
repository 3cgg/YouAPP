/**
 * 
 */
package j.jave.framework.components.file.service;

import java.net.URL;

import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.file.model.FileDist;

/**
 * @author Administrator
 *
 */
public interface FileDistService {

	public URL distribute(FileDist fileDist) throws ServiceException;
	
}
