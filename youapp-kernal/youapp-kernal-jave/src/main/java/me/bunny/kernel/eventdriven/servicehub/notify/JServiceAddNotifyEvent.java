/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceAddNotifyListener.class)
public class JServiceAddNotifyEvent extends JYouAPPEvent<JServiceAddNotifyEvent> {
	
	private final Class<?> serviceClass;
	
	public JServiceAddNotifyEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JServiceAddNotifyEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
