/**
 * 
 */
package j.jave.kernal.ehcache;

import java.io.File;
import java.io.InputStream;
import java.net.URI;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.jave.io.JClassRootPathResolver;
import net.sf.ehcache.CacheManager;


/**
 * thread safety.
 * @author J
 */
public class JDefaultEhcacheService extends JAbstractEhcacheService implements JEhcacheService{
	
	public JDefaultEhcacheService(){}
	
	private CacheManager cacheManager;
	
	/**
	 * init cache manager .
	 * @param configuration
	 */
	private void initCacheManager(InputStream inputStream){
		cacheManager=CacheManager.create(inputStream);
	}
	
	public JDefaultEhcacheService(JConfiguration configuration){
		JDefaultEhcacheServiceConfiguration ehcacheConfig=new JDefaultEhcacheServiceConfiguration();
		ehcacheConfig.setConfigLocation(configuration.getString(JEhcacheProperties.EHCACHE_CONFIG_LOCATION, "youappsub-ehcache.xml"));
		initCacheManager(getConfiguration(ehcacheConfig));
		produceEhcache();
	}
	
	/**
	 * default constructor.  use default xml in the JAR. 
	 */
	public JDefaultEhcacheService(JDefaultEhcacheServiceConfiguration configuration){
		initCacheManager(getConfiguration(configuration));
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

	private InputStream getConfiguration(JDefaultEhcacheServiceConfiguration configuration){
		try {
			String configLocation=configuration.getConfigLocation();
			URI configURI=new JClassRootPathResolver(configLocation).resolve();
			if(configURI==null||(!new File(configURI).exists())){
				return JDefaultEhcacheServiceConfiguration.class.getResourceAsStream(JEhcacheProperties.DEFAULT_EHCACHE);
			}
			return configURI.toURL().openStream();
		}catch (Exception e) {
			LOGGER.error(e.getMessage(), e);
		}
		return null ;
	}
	
	
}
