/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.ehcache.subhub;

import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicsupportcomp.support.ehcache.subhub.DefaultEhcacheServiceImpl")
public class DefaultEhcacheServiceImpl implements EhcacheService,JEhcacheServiceAware {

	private JDefaultEhcacheService defaultEhcacheService;
	
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
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.defaultEhcacheService = (JDefaultEhcacheService) ehcacheService;
	}
	
	@Override
	public JEhcacheService getEhcacheService() {
		return this.defaultEhcacheService;
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
	public void put(String key, int expiry, Object value) {
		defaultEhcacheService.put(key, expiry, value);
	}

}
