/**
 * 
 */
package j.jave.platform.sps.support.ehcache.subhub;

import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.ehcache.ext.JEhcacheInterfaceProvider;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(DefaultEhcacheDelegateService.BEAN_NAME)
public class DefaultEhcacheDelegateService implements EhcacheDelegateService {

	public static final String BEAN_NAME="defaultEhcacheDelegateService";
	
	
	private JDefaultEhcacheService defaultEhcacheService
	=JServiceHubDelegate.get().getService(this, JEhcacheInterfaceProvider.get().getServiceInterface());
	
	@Override
	public Object put(String key, Object object) {
		return defaultEhcacheService.put(key, object);
	}

	@Override
	public Object get(String key) {
		return defaultEhcacheService.get(key);
	}
	
	@Override
	public Object remove(String key) {
		return defaultEhcacheService.remove(key);
	}

	@Override
	public Object putNeverExpired(String key, Object object) {
		return defaultEhcacheService.putNeverExpired(key, object);
	}

	@Override
	public boolean contains(String key) {
		return defaultEhcacheService.contains(key);
	}

	@Override
	public Object put(String key, int expiry, Object value) {
		return defaultEhcacheService.put(key, expiry, value);
	}

}
