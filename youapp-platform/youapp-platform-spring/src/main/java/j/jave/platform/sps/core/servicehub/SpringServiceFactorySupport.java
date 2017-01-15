package j.jave.platform.sps.core.servicehub;

import java.lang.reflect.ParameterizedType;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JAbstractServiceFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

/**
 * Support all services registered in the spring container. 
 * <p><strong>override the method {@link #getService()} to expose the self.</strong> 
 * @author J
 * @param <T> the name service expose.  A full class name. 
 */
public  class SpringServiceFactorySupport<T extends JService> extends JAbstractServiceFactory<T> implements JServiceFactory<T> ,ApplicationContextAware 
,InitializingBean,BeanNameAware {

	protected final JLogger LOGGER=JLoggerFactory.getLogger(getClass());
	
	private ApplicationContext applicationContext=null;

	@Autowired
	private SpringApplicationServiceGetService springApplicationServiceGetService;
	
	@Autowired
	private SpringApplicationServiceNameCheckService serviceNameCheckService;
	
	protected T object=null;
	
	private String beanName;
	
	@Override
	public final void setBeanName(String name) {
		this.beanName=name;
	}
	
	/**
	 * the class registered in service hub.
	 */
	private final Class<T> registClass;;
	
	public SpringServiceFactorySupport(){
		if(this.getClass().getName().contains("$$EnhancerByCGLIB$$")){
			registClass=null;
		}
		else{
			ParameterizedType type= (ParameterizedType) this.getClass().getGenericSuperclass();
			this.registClass=(Class<T>) type.getActualTypeArguments()[0];
		}
	}
	
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
		
//		if(!serviceNameCheckService.valid(this.getClass())){
//			throw new JInitializationException(" the service/factory'name is not same as class name : "+this.getClass().getName());
//		}
		
		if(isCanRegister()){
			JServiceHubDelegate.get().register(this, registClass, this);
			LOGGER.info("registering service :["+registClass.getName()+"] powered by "+this.getClass().getName());
		}
		else{
			LOGGER.info("No registering service , ignore this service : ["+registClass.getName()+"] powered by "+this.getClass().getName());
		}
		
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
	}

	/**
	 * may be override the method by the sub-class. 
	 * default the method get the object-AOP .
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public T getService() {
		return (T) springApplicationServiceGetService.getService(applicationContext,this.getClass(),beanName);
	}

	@Override
	public Class<T> getServiceClass() {
		return registClass;
	}

	@Override
	public Class<?> getServiceImplClass() {
		return null;
	}
	
}
