package j.jave.kernal.memcached;

import me.bunny.kernel._c.service.JServiceAware;

public interface JMemcachedDisServiceAware extends JServiceAware {

	void setMemcachedDisService(JMemcachedDisService memcachedDisService);
	
	JMemcachedDisService getMemcachedDisService();
}
