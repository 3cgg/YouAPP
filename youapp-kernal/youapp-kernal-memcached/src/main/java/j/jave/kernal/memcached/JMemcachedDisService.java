package j.jave.kernal.memcached;

import j.jave.kernal.jave.service.JCacheService;
import j.jave.kernal.memcached.event.JMemcachedDisAddListener;
import j.jave.kernal.memcached.event.JMemcachedDisDeleteListener;
import j.jave.kernal.memcached.event.JMemcachedDisGetListener;
import j.jave.kernal.memcached.event.JMemcachedDisSetListener;


/**
 * the Memcached service interface .
 * @author J
 *
 */
public interface JMemcachedDisService extends JCacheService,JMemcachedDisAddListener ,JMemcachedDisDeleteListener,JMemcachedDisSetListener,JMemcachedDisGetListener {
	
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
