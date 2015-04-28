/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import j.jave.framework.servicehub.ehcache.JDefaultEhcacheServiceConfiguration;

/**
 * @author Administrator
 *
 */
public class DefaultSpringBeanSupportEhcacheServiceConfigure extends JDefaultEhcacheServiceConfiguration implements
		BeanSupportEhcacheServiceConfigure {

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
