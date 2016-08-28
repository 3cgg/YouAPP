package j.jave.platform.sps.support.memcached.subhub;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.cache.JInMemorySerializableCacheService;
import j.jave.kernal.memcached.event.JMemcachedDisAddEvent;
import j.jave.kernal.memcached.event.JMemcachedDisDeleteEvent;
import j.jave.kernal.memcached.event.JMemcachedDisGetEvent;
import j.jave.kernal.memcached.event.JMemcachedDisSetEvent;

public class MemoryInsteadOfMemcachedDelegateService
extends JServiceFactorySupport<MemoryInsteadOfMemcachedDelegateService>
	implements MemcachedDelegateService{
	
	private JInMemorySerializableCacheService inMemorySerializableService=
			JServiceHubDelegate.get().getService(this, JInMemorySerializableCacheService.class);

	
	@Override
	protected MemoryInsteadOfMemcachedDelegateService doGetService() {
		return this;
	}
	
	
	@Override
	public Object set(String key, int expiry, Object value) {
		return inMemorySerializableService.put(key, expiry, value);
	}

	@Override
	public Object get(String key) {
		return inMemorySerializableService.get(key);
	}

	@Override
	public void add(String key, int expiry, Object value) {
		inMemorySerializableService.put(key, expiry, value);
	}

	@Override
	public void delete(String key) {
		inMemorySerializableService.remove(key);
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return inMemorySerializableService.putNeverExpired(key, object);
	}

	@Override
	public Object remove(String key) {
		return inMemorySerializableService.remove(key);
	}

	@Override
	public boolean contains(String key) {
		return inMemorySerializableService.contains(key);
	}

	@Override
	public Object trigger(JMemcachedDisAddEvent event) {
		return putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisDeleteEvent event) {
		return remove(event.getKey());
	}

	@Override
	public Object trigger(JMemcachedDisSetEvent event) {
		return putNeverExpired(event.getKey(), event.getValue());
	}

	@Override
	public Object trigger(JMemcachedDisGetEvent event) {
		return get(event.getKey());
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		return inMemorySerializableService.put(key, expiry, value);
	}
	
}
