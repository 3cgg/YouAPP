/**
 * 
 */
package j.jave.framework.ehcache;

import j.jave.framework.io.JFile;

import java.io.IOException;
import java.io.InputStream;

import net.sf.ehcache.CacheManager;


/**
 * thread safety.
 * @author J
 */
public class JDefaultEhcacheService extends JAbstractEhcacheService implements JEhcacheService{
	
	private CacheManager cacheManager;
	
	/**
	 * file constructor
	 * @param file  the XML file for Ehcache, such as ehcache.xml
	 * @throws IOException 
	 */
	public JDefaultEhcacheService(JFile file) throws IOException {
		try {
			InputStream inputStream=file.getInputStream();
			initCacheManager(inputStream);
			produceEhcache();
		}catch (IOException e) {
			LOGGER.error(e.getMessage(), e);
			throw e;
		}
	}
	
	/**
	 * init cache manager .
	 * @param configuration
	 */
	private void initCacheManager(InputStream inputStream){
		cacheManager=CacheManager.create(inputStream);
		produceEhcache();
	}
	
	/**
	 * InputStream constructor
	 * @param inputStream
	 */
	public JDefaultEhcacheService(InputStream inputStream){
		initCacheManager(inputStream);
		produceEhcache();
	}
	
	/**
	 * default constructor.  use default xml in the JAR. 
	 */
	public JDefaultEhcacheService(JEhcacheServiceConfigure ehcacheServiceConfigure){
		initCacheManager(getConfiguration(ehcacheServiceConfigure));
		produceEhcache();
	}
	
	private void produceEhcache(){
		// init LinkedHashMap<Integer, Ehcache> ehcaches
		String[] cacheNames=cacheManager.getCacheNames();
		for (int i = 0; i < cacheNames.length; i++) {
			String cacheName=cacheNames[i];
			ehcaches.put(cacheName.hashCode(), cacheManager.getCache(cacheName));
		}
	}

	private InputStream getConfiguration(JEhcacheServiceConfigure ehcacheServiceConfigure){
		try {
			return ehcacheServiceConfigure.getConfigLocation().toURL().openStream();
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null ;
	}
	
	
}
