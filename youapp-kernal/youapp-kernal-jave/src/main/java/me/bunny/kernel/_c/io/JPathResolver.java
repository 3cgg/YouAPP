package me.bunny.kernel._c.io;

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
	public URI resolve() throws Exception;
	
	/**
	 * description 
	 * @return
	 */
	String getDescription();
	
}
