/**
 * 
 */
package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.jave.model.JModel;


/**
 * ANY resource that need be cached in any form.
 * @author J
 */
public interface ResourceCacheModel extends JModel{

	/**
	 * the resource uri.
	 * @return
	 */
	public String getUri();
	
}
