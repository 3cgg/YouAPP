package j.jave.framework.servicehub;

import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.logging.JLoggerFactory;

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
		LOGGER.info("register service :["+registClass.getName()+"]");
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
