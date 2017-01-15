package me.bunny.kernel.jave.support;

import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JCacheService;

public class JDefaultHashCacheService extends JServiceFactorySupport<JDefaultHashCacheService>
implements JCacheService {
	
	private JDefaultNewHashCacheService hashCache=new JDefaultNewHashCacheService();
	
	@Override
	public JDefaultHashCacheService doGetService() {
		return this;
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return hashCache.putNeverExpired(key, object);
	}

	@Override
	public Object get(String key) {
		return hashCache.get(key);
	}

	@Override
	public Object remove(String key) {
		return hashCache.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return hashCache.contains(key);
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		return hashCache.put(key, expiry, value);
	}

}
