package j.jave.framework.commons.memcached;

import j.jave.framework.commons.service.JServiceAware;

public interface JMemcachedDisServiceAware extends JServiceAware {

	void setMemcachedDisService(JMemcachedDisService memcachedDisService);
	
	JMemcachedDisService getMemcachedDisService();
}
