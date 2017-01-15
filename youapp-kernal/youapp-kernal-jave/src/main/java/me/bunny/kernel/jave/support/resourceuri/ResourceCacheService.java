/**
 * 
 */
package me.bunny.kernel.jave.support.resourceuri;

import me.bunny.kernel.jave.io.memory.JSingleDynamicMemoryCacheIO;



/**
 * system resource interface.
 * @author J
 */
public interface ResourceCacheService<T> extends InitialResource,ResourceCacheRefreshListener,
	JSingleDynamicMemoryCacheIO<T>{
	
	IdentifierGenerator generator();
	
}
