/**
 * 
 */
package j.jave.framework.commons.ehcache;

import j.jave.framework.commons.logging.JLogger;
import j.jave.framework.commons.logging.JLoggerFactory;
import j.jave.framework.commons.support.JObjectLoop;
import net.sf.ehcache.Ehcache;
import net.sf.ehcache.Element;

/**
 * super-class . 
 * @author J
 */
public abstract class JAbstractEhcacheService implements JEhcacheService {

	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	protected JObjectLoop<Integer, Ehcache> ehcaches=new JObjectLoop<Integer, Ehcache>();

	/**
	 * {@inheritDoc }
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
	 * {@inheritDoc }
	 */
	@Override
	public Object remove(String key) {
		Ehcache cache=getEhcache(key);
		Object pre=cache.get(key);
		cache.remove(key);
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
	
	/**
	 * {@inheritDoc }
	 */
	@Override
	public Object get(String key) {
		Ehcache cache=getEhcache(key);
		Element element=cache.get(key);
		if(element!=null){
			return element.getObjectValue();
		}
		return null;
	}
	
	
}
