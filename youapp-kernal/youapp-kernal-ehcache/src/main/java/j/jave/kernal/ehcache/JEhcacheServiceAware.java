package j.jave.kernal.ehcache;

import me.bunny.kernel._c.service.JServiceAware;

public interface JEhcacheServiceAware  extends JServiceAware{

	public void setEhcacheService(JEhcacheService ehcacheService) ;
	
	/**
	 * the method is deprecated as the service is got via lazy getting mechanism.
	 * @return
	 */
	@Deprecated
	JEhcacheService getEhcacheService();
	
}
