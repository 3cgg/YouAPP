/**
 * 
 */
package j.jave.kernal.jave.support.resourceuri;

import j.jave.kernal.jave.io.memory.JSingleDynamicMemoryCacheIO;



/**
 * system resource interface.
 * @author J
 */
public interface ResourceCacheService<T> extends InitialResource,ResourceCacheRefreshListener,
	JSingleDynamicMemoryCacheIO<T>{
	
	IdentifierGenerator generator();
	
}
