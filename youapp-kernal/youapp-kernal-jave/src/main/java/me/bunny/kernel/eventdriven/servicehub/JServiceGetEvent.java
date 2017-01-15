/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub;



/**
 * service get event object. 
 * @author J
 */
@SuppressWarnings("serial")
@JListenerOnEvent(name=JServiceGetListener.class)
class JServiceGetEvent extends JYouAPPEvent<JServiceGetEvent> {

	private final Class<?> serviceName;
	
	public JServiceGetEvent(Object source,Class<?> serviceName) {
		super(source, HIGEST);
		this.serviceName=serviceName;
	}
	
	public Class<?> getServiceName() {
		return serviceName;
	}

	
	

}
