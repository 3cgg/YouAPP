/**
 * 
 */
package j.jave.framework.commons.ehcache;

import j.jave.framework.commons.service.JCacheService;

/**
 * @author J
 */
public interface JEhcacheService extends JCacheService {

	/**
	 * put object into cache.
	 * @param key
	 * @param object
	 * @return
	 */
	public Object put(String key,Object object);
	
	/**
	 * get cached object from cache.
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * remove object from cache.
	 * @param key
	 * @return
	 */
	public Object remove(String key);
}
