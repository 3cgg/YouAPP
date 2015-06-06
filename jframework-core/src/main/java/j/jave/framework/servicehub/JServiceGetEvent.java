/**
 * 
 */
package j.jave.framework.servicehub;

import j.jave.framework.listener.JAPPEvent;


/**
 * service get event object. 
 * @author J
 */
@JListenerOnEvent(name=JServiceGetListener.class)
class JServiceGetEvent extends JAPPEvent<JServiceGetEvent> {

	private final Class<?> serviceName;
	
	public JServiceGetEvent(Object source,Class<?> serviceName) {
		super(source, HIGEST);
		this.serviceName=serviceName;
	}
	
	public Class<?> getServiceName() {
		return serviceName;
	}

	
	

}
