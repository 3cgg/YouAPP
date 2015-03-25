/**
 * 
 */
package j.jave.framework.components.memcached.inhub;

import j.jave.framework.components.core.hub.ServiceFactory;
import j.jave.framework.components.core.hub.ServiceHub;
import j.jave.framework.components.memcached.JMemcachedDistService;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="memcachedServiceFactory")
public class MemcachedServiceFactory implements ServiceFactory<JMemcachedDistService> , ApplicationContextAware ,InitializingBean  {

	private ApplicationContext applicationContext=null;
	
	@Override
	public JMemcachedDistService getService() {
		return applicationContext.getBean("simpleJMemcachedDist", JMemcachedDistService.class); 
	}
	
	/* (non-Javadoc)
	 * @see org.springframework.context.ApplicationContextAware#setApplicationContext(org.springframework.context.ApplicationContext)
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}
	
	
	/* (non-Javadoc)
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		ServiceHub.get().register(ServiceHub.StandardServiceInterfaces.MEMCACHED_DIST_SERVICE, this);
	}
}
