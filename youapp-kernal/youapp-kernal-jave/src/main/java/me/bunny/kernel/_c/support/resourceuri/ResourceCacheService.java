/**
 * 
 */
package me.bunny.kernel._c.support.resourceuri;

import me.bunny.kernel._c.io.memory.JSingleDynamicMemoryCacheIO;



/**
 * system resource interface.
 * @author J
 */
public interface ResourceCacheService<T> extends InitialResource,ResourceCacheRefreshListener,
	JSingleDynamicMemoryCacheIO<T>{
	
	IdentifierGenerator generator();
	
}
