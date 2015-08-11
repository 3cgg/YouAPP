/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import j.jave.framework.commons.ehcache.JDefaultEhcacheService;
import j.jave.framework.commons.ehcache.JEhcacheService;
import j.jave.framework.commons.ehcache.JEhcacheServiceAware;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultEhcacheServiceImpl")
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

}
