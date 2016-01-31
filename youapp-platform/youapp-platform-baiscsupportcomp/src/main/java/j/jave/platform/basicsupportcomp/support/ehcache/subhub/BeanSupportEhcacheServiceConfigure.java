/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.ehcache.subhub;

import j.jave.kernal.ehcache.JEhcacheServiceConfigure;

/**
 * @author Administrator
 *
 */
public interface BeanSupportEhcacheServiceConfigure extends
		JEhcacheServiceConfigure {

	public void setEhcacheBeanName(String ehcacheBeanName);
	
	public String getEhcacheBeanName() ;
	
}
