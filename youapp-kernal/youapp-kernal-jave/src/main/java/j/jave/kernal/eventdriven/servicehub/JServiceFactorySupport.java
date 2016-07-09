package j.jave.kernal.eventdriven.servicehub;

import j.jave.kernal.eventdriven.servicehub.aop.JServiceMethodInterceptor;
import j.jave.kernal.jave.aop.JAdvisedSupport;
import j.jave.kernal.jave.aop.JAspectJAroundAdvice;
import j.jave.kernal.jave.aop.JCglibAopProxy;
import j.jave.kernal.jave.aop.JTargetSource;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.aopalliance.intercept.MethodInvocation;

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

	protected Object proxy=null;
	
	private Object sync=new Object();
	
	/**
	 * the class registered in service hub.
	 */
	private final Class<T> registClass;;
	
	@SuppressWarnings("unchecked")
	public JServiceFactorySupport(){
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
	public JServiceFactorySupport(Class<T> registClass){
		this.registClass=registClass;
	}
	
	/**
	 * call to register the service in service hub. 
	 * <strong>Never called by subclasses</strong>
	 * @throws Exception
	 */
	public final void postRegister() throws Exception {
		if(isCanRegister()){
//			getService();
			JServiceHubDelegate.get().register(this, registClass, this);
			LOGGER.info("registering service :["+registClass.getName()+"] powered by "+this.getClass().getName());
		}
		else{
			LOGGER.info("No registering service , ignore this service : ["+registClass.getName()+"] powered by "+this.getClass().getName());
		}
	}

	@Override
	public Class<T> getServiceClass() {
		return registClass;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return this.getClass();
	}
	
	/**
	 * @return
	 */
	@Override
	public final T getService() {
		try{
			if(JServiceMethodInterceptor.class.isAssignableFrom(getServiceImplClass())){
				return doGetService();
			}
			else{
				//proxy
				if(proxy==null){
					synchronized (sync) {
						if(proxy==null){
							//get aspectJ
							Collection<JServiceFactory<?>> factories=JServiceHubDelegate.get().getInterceptors();
							List<JServiceFactory<?>> targetInterceptorFactories=new ArrayList<JServiceFactory<?>>();
							for(JServiceFactory<?> factory:factories){
								JServiceMethodInterceptor<?> methodInterceptor= (JServiceMethodInterceptor<?>) factory.getService();
								if(methodInterceptor.accept(getServiceClass())){
									targetInterceptorFactories.add(factory);
								}
							}
							
							List<Object> aopInterceptors=new ArrayList<Object>();
							for(JServiceFactory<?> factory:targetInterceptorFactories){
								JServiceFactoryAspectInstanceFactory serviceFactoryAspectInstanceFactory
								=new JServiceFactoryAspectInstanceFactory(factory);
								
								Method aspectJMethod=factory.getServiceClass().getMethod("invoke", MethodInvocation.class);
								
								JAspectJAroundAdvice aroundAdvice=new JAspectJAroundAdvice(aspectJMethod, serviceFactoryAspectInstanceFactory);
								aroundAdvice.setAspectName(factory.getServiceClass().getName());
								
								aopInterceptors.add(aroundAdvice);
							}
							
							JAdvisedSupport advisedSupport=new JAdvisedSupport();
							advisedSupport.setInterceptors(aopInterceptors);
							
							JTargetSource targetSource=new JServiceFactoryTargetSource(this);
							advisedSupport.setTargetSource(targetSource);
							
							JCglibAopProxy cglibAopProxy=new JCglibAopProxy(advisedSupport);
							try{
								proxy= (T) cglibAopProxy.getProxy();
							}catch(Exception e){
								LOGGER.error(" cannot proxy for class : "+ this.getServiceClass() + " ,"+getServiceImplClass());
								throw e;
							}
							
						}
					}
				}
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
		return (T) proxy;
	}
	
	/**
	 * override the method by the sub-class. 
	 * @return
	 */
	protected T doGetService(){
		throw new RuntimeException("override the method to provide concrete service,"+this.getClass().getName());
	}
	
	T innerGetService(){
		return doGetService();
	}

	protected boolean isSingleton(){
		return true;
	}
	
}
