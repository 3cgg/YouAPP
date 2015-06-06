/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;


/**
 * service register event object. 
 * @author J
 */
@JListenerOnEvent(name=JServiceRegisterListener.class)
class JServiceRegisterEvent extends JAPPEvent<JServiceRegisterEvent> {
	
	private final Class<?> serviceName;
	private JServiceFactory<?> serviceFactory;
	
	public JServiceRegisterEvent(Object source,Class<?> serviceName,JServiceFactory<?> serviceFactory) {
		super(source, HIGEST);
		this.serviceName=serviceName;
		this.serviceFactory=serviceFactory;
	}
	
	public Class<?> getServiceName() {
		return serviceName;
	}
	
	public JServiceFactory<?> getServiceFactory() {
		return serviceFactory;
	}
}
