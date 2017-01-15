/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceExistsListener.class)
public class JServiceExistsEvent extends JYouAPPEvent<JServiceExistsEvent> {
	
	private final Class<?> serviceClass;
	
	public JServiceExistsEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JServiceExistsEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
