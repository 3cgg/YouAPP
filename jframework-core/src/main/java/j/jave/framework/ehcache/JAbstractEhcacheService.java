/**
 * 
 */
package j.jave.framework.ehcache;

import j.jave.framework.support.ObjectLoop;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * super-class . 
 * @author J
 */
public abstract class JAbstractEhcacheService implements JEhcacheService {

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	protected ObjectLoop<Integer, Ehcache> ehcaches=new ObjectLoop<Integer, Ehcache>();

	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheService#put(java.lang.String, java.lang.Object)
	 */
	@Override
	public Object put(String key, Object object) {
		Ehcache cache=getEhcache(key);
		Object pre=cache.get(key);
		Element element=new Element(key, object);
		cache.put(element);
		return pre;
	}
	/**
	 * delegate to the object {@link Ehcache}
	 * @param key
	 * @return
	 */
	private Ehcache getEhcache(String key){
		return ehcaches.get(key.hashCode());
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheService#get(java.lang.String)
	 */
	@Override
	public Object get(String key) {
		Ehcache cache=getEhcache(key);
		return cache.get(key);
	}
	
	
}
