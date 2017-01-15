package me.bunny.kernel.jave.support;

import java.util.concurrent.ConcurrentHashMap;

import me.bunny.kernel.jave.logging.JLogger;
import me.bunny.kernel.jave.logging.JLoggerFactory;
import me.bunny.kernel.jave.service.JCacheService;

public class JDefaultNewHashCacheService
implements JCacheService {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(JDefaultNewHashCacheService.class);
	
	private ConcurrentHashMap<String, Object> cache=new ConcurrentHashMap<String, Object>();
	
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
