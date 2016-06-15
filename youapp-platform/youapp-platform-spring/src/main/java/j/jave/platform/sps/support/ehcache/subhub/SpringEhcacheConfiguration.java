/**
 * 
 */
package j.jave.platform.sps.support.ehcache.subhub;

import j.jave.kernal.ehcache.JDefaultEhcacheServiceConfiguration;

/**
 * @author J
 *
 */
public class SpringEhcacheConfiguration extends JDefaultEhcacheServiceConfiguration{

	protected String ehcacheFactoryBeanName;

	public String getEhcacheFactoryBeanName() {
		return ehcacheFactoryBeanName;
	}

	public void setEhcacheFactoryBeanName(String ehcacheFactoryBeanName) {
		this.ehcacheFactoryBeanName = ehcacheFactoryBeanName;
	}

}
