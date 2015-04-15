package j.jave.framework.components.core.servicehub;

import j.jave.framework.servicehub.JAbstractServiceFactory;
import j.jave.framework.servicehub.JService;
import j.jave.framework.servicehub.JServiceFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Support all services registered in the spring container. 
 * override the method {@method getService} to expose the self. 
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
	
	@Override
	public void afterPropertiesSet() throws Exception {
		getService();
		new ServiceHubDelegate().register(this, registClass, this);
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
		throw new RuntimeException("you should extend this class.");
	}
	
}
