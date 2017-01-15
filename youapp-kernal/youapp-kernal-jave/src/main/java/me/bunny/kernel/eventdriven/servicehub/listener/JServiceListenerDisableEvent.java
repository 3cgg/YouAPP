/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPListener;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceListenerDisableListener.class)
public class JServiceListenerDisableEvent extends JYouAPPEvent<JServiceListenerDisableEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	private final Class<? extends JYouAPPListener> listener;
	
	public JServiceListenerDisableEvent(Object source,Class<?> serviceClass,Class<? extends JYouAPPListener> listener) {
		super(source);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public JServiceListenerDisableEvent(Object source,int priority ,Class<?> serviceClass,Class<? extends JYouAPPListener> listener) {
		super(source,priority);
		this.serviceClass=serviceClass;
		this.listener=listener;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
	public Class<? extends JYouAPPListener> getListener() {
		return listener;
	}
}
