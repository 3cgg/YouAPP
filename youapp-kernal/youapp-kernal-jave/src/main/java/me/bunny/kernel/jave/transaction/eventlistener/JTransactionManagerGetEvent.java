/**
 * 
 */
package me.bunny.kernel.jave.transaction.eventlistener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JTransactionManagerGetListener.class)
public class JTransactionManagerGetEvent extends JYouAPPEvent<JTransactionManagerGetEvent> {
	
	private final Class<?> serviceClass;
	
	public JTransactionManagerGetEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JTransactionManagerGetEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
