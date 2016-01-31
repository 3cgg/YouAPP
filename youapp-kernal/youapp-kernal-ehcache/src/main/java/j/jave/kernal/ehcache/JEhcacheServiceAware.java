package j.jave.kernal.ehcache;

import j.jave.kernal.jave.service.JServiceAware;

public interface JEhcacheServiceAware  extends JServiceAware{

	public void setEhcacheService(JEhcacheService ehcacheService) ;
	
	JEhcacheService getEhcacheService();
	
}
