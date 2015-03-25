package j.jave.framework.components.memcached;

import j.jave.framework.components.core.hub.Service;

public interface JMemcachedDistService extends Service {
	
	public void set(String key , int expiry, Object value) ;
	
	public Object get(String key);
	
	public void add(String key , int expiry, Object value) ;
	
	public void delete(String key);
}
