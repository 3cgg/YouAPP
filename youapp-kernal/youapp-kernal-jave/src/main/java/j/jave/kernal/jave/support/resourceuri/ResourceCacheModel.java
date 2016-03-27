/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.model.JModel;


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
