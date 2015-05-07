/**
 * 
 */
package j.jave.framework.components.memory;

import j.jave.framework.servicehub.JService;


/**
 * the service support the case of what a response need be cached. 
 * @author J
 */
public interface MemoryCachedService<T> extends JService,ResourceCachedRefreshListener {

	boolean isNeedCache(String key);

	void add(T object);

	void remove(T object);
	
	T get(String path);
	
}
