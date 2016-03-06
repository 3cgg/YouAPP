/**
 * 
 */
package j.jave.kernal.ehcache;


/**
 * @author J
 */
public class JDefaultEhcacheServiceConfiguration {
	
	protected String configLocation=JEhcacheProperties.DEFAULT_EHCACHE;

	public void setConfigLocation(String configLocation) {
		this.configLocation=configLocation;
	}
	public String getConfigLocation() {
		return configLocation;
	}
}
