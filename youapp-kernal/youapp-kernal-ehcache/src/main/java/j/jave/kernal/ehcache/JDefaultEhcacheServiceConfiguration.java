/**
 * 
 */
package j.jave.kernal.ehcache;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author J
 */
public class JDefaultEhcacheServiceConfiguration implements
		JEhcacheServiceConfigure {
	
	protected String configLocation="youapp-ehcache.xml";
	
	protected List<String> cacheNames=new ArrayList<String>();
	
	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheServiceConfigure#setConfigLocation(java.lang.String)
	 */
	@Override
	public void setConfigLocation(String configLocation) {
		this.configLocation=configLocation;
	}

	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheServiceConfigure#setCacheNames(java.util.List)
	 */
	@Override
	public void setCacheNames(List<String> cacheNames) {
		this.cacheNames=cacheNames;
	}

	/**
	 * @return the cacheNames
	 */
	public List<String> getCacheNames() {
		return cacheNames;
	}
	
	/* (non-Javadoc)
	 * @see j.jave.framework.ehcache.JEhcacheServiceConfigure#getConfigLocation()
	 */
	@Override
	public URI getConfigLocation() {
		try {
			return JDefaultEhcacheServiceConfiguration.class.getResource(configLocation).toURI();
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
	
}
