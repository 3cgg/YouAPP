package j.jave.framework.commons.ehcache;

import j.jave.framework.commons.service.JServiceAware;

public interface JEhcacheServiceAware  extends JServiceAware{

	public void setEhcacheService(JEhcacheService ehcacheService) ;
	
	JEhcacheService getEhcacheService();
	
}
