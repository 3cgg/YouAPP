/**
 * 
 */
package j.jave.framework.components.core.servicehub;

import j.jave.framework.servicehub.JServiceFactory;
import j.jave.framework.servicehub.JServiceGetEvent;
import j.jave.framework.servicehub.JServiceListener;
import j.jave.framework.servicehub.JServiceRegisterEvent;

/**
 * delegate to use event mechanism to get service from hub.   
 * @author J
 */
public class ServiceHubDelegate {

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
	
	@SuppressWarnings("unchecked")
	public  <T> T getService(Object object,Class<T> clazz){
		JServiceGetEvent getEvent=new JServiceGetEvent(object, clazz);
		return (T) new JServiceListener().trigger(getEvent);
	}
	
	
}
