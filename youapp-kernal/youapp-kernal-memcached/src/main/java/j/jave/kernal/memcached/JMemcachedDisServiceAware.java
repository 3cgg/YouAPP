package j.jave.kernal.memcached;

import j.jave.kernal.jave.service.JServiceAware;

public interface JMemcachedDisServiceAware extends JServiceAware {

	void setMemcachedDisService(JMemcachedDisService memcachedDisService);
	
	JMemcachedDisService getMemcachedDisService();
}
