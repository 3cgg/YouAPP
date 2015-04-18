/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import org.springframework.stereotype.Service;

import j.jave.framework.ehcache.JDefaultEhcacheService;

/**
 * @author J
 */
@Service(value="defaultEhcacheServiceImpl")
public class DefaultEhcacheServiceImpl implements EhcacheService {

	private JDefaultEhcacheService defaultEhcacheService;
	
	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheService#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object object) {
		return defaultEhcacheService.put(key, object);
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheService#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		return defaultEhcacheService.get(key);
	}
	
	/**
	 * @param defaultEhcacheService the defaultEhcacheService to set
	 */
	public void setDefaultEhcacheService(
			JDefaultEhcacheService defaultEhcacheService) {
		this.defaultEhcacheService = defaultEhcacheService;
	}

}
