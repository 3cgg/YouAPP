package j.jave.platform.basicsupportcomp.core.servicehub;

import j.jave.kernal.eventdriven.servicehub.JAbstractServiceFactory;
import j.jave.kernal.eventdriven.servicehub.JServiceFactory;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.service.JService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Support all services registered in the spring container. 
 * <p><strong>override the method {@link #getService()} to expose the self.</strong> 
 * @author J
 * @param <T> the name service expose.  A full class name. 
 */
public  class SpringServiceFactorySupport<T extends JService> extends JAbstractServiceFactory<T> implements JServiceFactory<T> ,ApplicationContextAware ,InitializingBean {

	protected final Logger LOGGER=LoggerFactory.getLogger(getClass());
	
	private ApplicationContext applicationContext=null;

	protected T object=null;
	
	/**
	 * the class registered in service hub.
	 */
	private final Class<T> registClass;;
	
	/**
	 * register a class 
	 * @param registClass
	 */
	public SpringServiceFactorySupport(Class<T> registClass){
		this.registClass=registClass;
	}
	
	protected final T getBeanByName(String beanName){
		object=applicationContext.getBean(beanName,registClass);
		return object;
	}
	
	protected final <M> M getBeanByName(String beanName,Class<M> clazz){
		return (M) applicationContext.getBean(beanName,clazz);
	}
	
	@Override
	public void afterPropertiesSet() throws Exception {
		getService();
		JServiceHubDelegate.get().register(this, registClass, this);
		LOGGER.info("register service :["+registClass.getName()+"]");
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	/**
	 * override the method by the sub-class. 
	 * @return
	 */
	@Override
	public T getService() {
		throw new RuntimeException("override the method to provide concrete service.");
	}
	
}
