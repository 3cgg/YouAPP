package j.jave.kernal.jave.service;

public interface JCacheService extends JService{

	/**
	 * put object into cache, the object never expired,
	 * unless the specified cache mechanism does not support
	 * @param key
	 * @param object
	 * @return the previous object related to the key if any
	 */
	public Object putNeverExpired(String key,Object object);
	
	/**
	 * always get cached object if the key is existing.  
	 * @param key
	 * @return
	 */
	public Object get(String key);
	
	/**
	 * remove object from cache, it means frequently the method {@link #get(String)} never get anyone
	 * @param key
	 * @return
	 */
	public Object remove(String key);
	
	/**
	 * test whether the key is cached.
	 * @param key
	 * @return
	 */
	public boolean contains(String key);
	
}
