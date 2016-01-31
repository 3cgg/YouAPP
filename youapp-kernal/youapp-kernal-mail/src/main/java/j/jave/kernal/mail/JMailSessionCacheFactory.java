package j.jave.kernal.mail;

import j.jave.kernal.jave.service.JCacheService;

/**
 * implement the interface to customize the mail session cache factory to provide the cache mechanism 
 * @author J
 *
 */
public interface JMailSessionCacheFactory {
	
	/**
	 *  always return a singleton object reference to the {@link JCacheService}
	 * @return
	 */
	JCacheService getCacheService();
	
	
}
