package j.jave.framework.servicehub.memcached;

import j.jave.framework.servicehub.JService;


/**
 * the Memcached service interface .
 * @author J
 *
 */
public interface JMemcachedDisService extends JService,JMemcachedDisAddListener ,JMemcachedDisDeleteListener,JMemcachedDisSetListener,JMemcachedDisGetListener {
	
	/**
	 * put value to  remote cache
	 * @param key
	 * @param expiry
	 * @param value
	 * @return previous value before setting.
	 */
	public Object set(String key , int expiry, Object value) ;
	
	/**
	 * get value from  remote cache
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * add value to  remote cache
	 * @param key
	 * @param expiry
	 * @param value
	 */
	public void add(String key , int expiry, Object value) ;
	
	/**
	 * delete from remote cache. 
	 * @param key
	 */
	public void delete(String key);
}
