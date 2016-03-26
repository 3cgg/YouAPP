/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.ehcache.subhub;

import j.jave.kernal.ehcache.JDefaultEhcacheServiceConfiguration;

/**
 * @author J
 *
 */
public class SpringEhcacheConfiguration extends JDefaultEhcacheServiceConfiguration{

	protected String ehcacheBeanName;
	
	/**
	 * @param ehcacheBeanName the ehcacheBeanName to set
	 */
	public void setEhcacheBeanName(String ehcacheBeanName) {
		this.ehcacheBeanName = ehcacheBeanName;
	}
	
	/**
	 * @return the ehcacheBeanName
	 */
	public String getEhcacheBeanName() {
		return ehcacheBeanName;
	}

	

}
