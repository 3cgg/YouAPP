package j.jave.kernal.jave.support;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JCacheService;

import java.util.concurrent.ConcurrentHashMap;

public class JDefaultNewHashCacheService extends JServiceFactorySupport<JDefaultNewHashCacheService>
implements JCacheService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JDefaultNewHashCacheService.class);
	
	private ConcurrentHashMap<String, Object> cache=new ConcurrentHashMap<String, Object>();
	
	@Override
	public JDefaultNewHashCacheService getService() {
		return new JDefaultNewHashCacheService();
	}
	
	@Override
	public Object putNeverExpired(String key, Object object) {
		return cache.put(key, object);
	}

	@Override
	public Object get(String key) {
		return cache.get(key);
	}

	@Override
	public Object remove(String key) {
		return cache.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return cache.contains(key);
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		Object obj=putNeverExpired(key, value);
		LOGGER.warn("currently, hash cache doesnot support expired time, so the cache object is never expired.");
		return obj;
	}

}
