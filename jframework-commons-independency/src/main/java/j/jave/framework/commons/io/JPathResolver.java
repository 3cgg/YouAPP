package j.jave.framework.commons.io;

import java.net.URI;


/**
 * resolver path .
 * @author J
 *
 */
public interface JPathResolver {

	/**
	 * get actual path.
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public URI resolver() throws Exception;
	
	/**
	 * description 
	 * @return
	 */
	String getDescription();
	
}
