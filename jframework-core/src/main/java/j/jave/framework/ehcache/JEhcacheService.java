/**
 * 
 */
package j.jave.framework.ehcache;

import j.jave.framework.servicehub.JService;

/**
 * @author J
 */
public interface JEhcacheService extends JService {

	public Object put(String key,Object object);
	
	public Object get(String key);
}
