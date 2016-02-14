/**
 * 
 */
package j.jave.kernal.ehcache;

import java.net.URI;
import java.util.List;

/**
 * @author J
 */
public interface JEhcacheServiceConfigure {

	public void setConfigLocation(String configLocation);
	
	/**
	 * resolve the configuration to URI, from which can get inputstream
	 * @return
	 */
	public URI getConfigLocation();
	
	public void setCacheNames(List<String> cacheNames);
	
	
	
}