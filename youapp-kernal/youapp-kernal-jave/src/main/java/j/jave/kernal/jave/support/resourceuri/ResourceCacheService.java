/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.service.JService;


/**
 * system resource interface.
 * @author J
 */
public interface ResourceCacheService extends JService, ResourceCacheServiceGetListener{
	
	IdentifierGenerator generator();
	
}
