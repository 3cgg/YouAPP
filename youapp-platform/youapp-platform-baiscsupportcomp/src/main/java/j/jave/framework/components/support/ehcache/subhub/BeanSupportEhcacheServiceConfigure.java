/**
 * 
 */
package j.jave.framework.components.support.ehcache.subhub;

import j.jave.framework.commons.ehcache.JEhcacheServiceConfigure;

/**
 * @author Administrator
 *
 */
public interface BeanSupportEhcacheServiceConfigure extends
		JEhcacheServiceConfigure {

	public void setEhcacheBeanName(String ehcacheBeanName);
	
	public String getEhcacheBeanName() ;
	
}
