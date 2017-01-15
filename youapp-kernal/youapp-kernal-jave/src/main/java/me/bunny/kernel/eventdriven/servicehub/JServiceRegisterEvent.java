/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;



/**
 * service register event object. 
 * @author J
 */
@SuppressWarnings("serial")
@JListenerOnEvent(name=JServiceRegisterListener.class)
class JServiceRegisterEvent extends JYouAPPEvent<JServiceRegisterEvent> {
	
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
