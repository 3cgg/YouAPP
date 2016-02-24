package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

import java.lang.reflect.ParameterizedType;

/**
 * the super class that supports the single service of the platform.
 * expose the method {@link #postRegister()} to called by service manager to register the service.
 * the service is not never registered by any other platforms such as Spring, OSGI. 
 * ALL services need be manually registered by calling {@link JServiceFactoryManager #registerAllServices()}.
 * Please be aware how to define service resource definition, a services.properties file needed. 
 * <p><strong>Note ensure that any service is only unit and singleton , not any dependences on each other while initializing the instance, 
 * otherwise a cycle dependence causes any unexpected exception.</strong>
 * 
 * the subclasses should have an empty constructor.
 * @author J
 *
 * @param <T>
 */
public class JServiceFactorySupport<T extends JService> extends JAbstractServiceFactory<T> implements JServiceFactoryRegister{
	
	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());

	protected T object=null;
	
	/**
	 * the class registered in service hub.
	 */
	private final Class<T> registClass;;
	
	@SuppressWarnings("unchecked")
	public JServiceFactorySupport(){
		ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
		this.registClass=(Class<T>) type.getActualTypeArguments()[0];
	}
	
	/**
	 * register a class 
	 * @param registClass
	 */
	public JServiceFactorySupport(Class<T> registClass){
		this.registClass=registClass;
	}
	
	/**
	 * call to register the service in service hub. 
	 * <strong>Never called by subclasses</strong>
	 * @throws Exception
	 */
	public final void postRegister() throws Exception {
		getService();
		JServiceHubDelegate.get().register(this, registClass, this);
		LOGGER.info("registering service :["+registClass.getName()+"]");
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
