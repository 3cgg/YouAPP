package j.jave.kernal.jave.support;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JCacheService;

import java.util.concurrent.ConcurrentHashMap;

public class JDefaultHashCacheService extends JServiceFactorySupport<JDefaultHashCacheService>
implements JCacheService {
	
	private ConcurrentHashMap<String, Object> cache=new ConcurrentHashMap<String, Object>();
	
	@Override
	public JDefaultHashCacheService getService() {
		return this;
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

}
