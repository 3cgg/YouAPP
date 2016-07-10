/**
 * 
 */
package j.jave.kernal.jave.transaction.eventlistener;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
