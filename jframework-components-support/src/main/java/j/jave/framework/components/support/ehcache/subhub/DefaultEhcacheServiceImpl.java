/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import j.jave.framework.commons.ehcache.JDefaultEhcacheService;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="defaultEhcacheServiceImpl")
public class DefaultEhcacheServiceImpl implements EhcacheService {

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
	
	/**
	 * @param defaultEhcacheService the defaultEhcacheService to set
	 */
	public void setDefaultEhcacheService(
			JDefaultEhcacheService defaultEhcacheService) {
		this.defaultEhcacheService = defaultEhcacheService;
	}

}
