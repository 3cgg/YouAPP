/**
 * 
 */
package j.jave.framework.components.core.servicehub;

import j.jave.framework.listener.JAPPEvent;
import j.jave.framework.servicehub.JServiceEventProcessor;
import j.jave.framework.servicehub.JServiceFactory;
import j.jave.framework.servicehub.JServiceGetEvent;
import j.jave.framework.servicehub.JServiceListener;
import j.jave.framework.servicehub.JServiceRegisterEvent;

/**
 * delegate to use event mechanism to get service from hub.   
 * @author J
 */
public class ServiceHubDelegate {

	private static ServiceHubDelegate serviceHubDelegate=null;
	private ServiceHubDelegate(){}
	
	public static ServiceHubDelegate get(){
		
		if(serviceHubDelegate==null){
			synchronized (ServiceHubDelegate.class) {
				serviceHubDelegate=new ServiceHubDelegate();
			}
		}
		return serviceHubDelegate;
	}
	
	private final JServiceEventProcessor serviceEventProcessor=JServiceEventProcessor.get();
	
	/**
	 * register service 
	 * @param object
	 * @param serviceName
	 * @param serviceFactory
	 */
	public void register(Object object,Class<?> serviceName,JServiceFactory<?> serviceFactory){
		JServiceRegisterEvent registerEvent=new JServiceRegisterEvent(object, serviceName, serviceFactory);
		new JServiceListener().trigger(registerEvent);
	}
	
	/**
	 * get service 
	 * @param object
	 * @param clazz
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public  <T> T getService(Object object,Class<T> clazz){
		JServiceGetEvent getEvent=new JServiceGetEvent(object, clazz);
		return (T) new JServiceListener().trigger(getEvent);
	}
	
	/**
	 * delegate the event to the event processor,
	 * the event later is to be executed by processor,  unexpected immediately
	 * @param event
	 */
	public void addDelayEvent(JAPPEvent<?> event){
		serviceEventProcessor.addEvent(event);
	}
	
}
