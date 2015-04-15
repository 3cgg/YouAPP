package j.jave.framework.components.support.memcached.subhub;

import j.jave.framework.memcached.JDefaultMemcachedDisService;

import org.springframework.stereotype.Service;

@Service(value="defaultMemcachedServiceImpl")
public class DefaultMemcachedServiceImpl implements MemcachedService {
	
	private JDefaultMemcachedDisService defaultMemcachedDisService; 

	void setDefaultMemcachedDisService(
			JDefaultMemcachedDisService defaultMemcachedDisService) {
		this.defaultMemcachedDisService = defaultMemcachedDisService;
	}
	
	@Override
	public void set(String key, int expiry, Object value) {
		defaultMemcachedDisService.set(key, expiry, value);
	}

	@Override
	public Object get(String key) {
		return defaultMemcachedDisService.get(key);
	}

	@Override
	public void add(String key, int expiry, Object value) {
		defaultMemcachedDisService.add(key, expiry, value);
	}

	@Override
	public void delete(String key) {
		defaultMemcachedDisService.delete(key);
	}
}
