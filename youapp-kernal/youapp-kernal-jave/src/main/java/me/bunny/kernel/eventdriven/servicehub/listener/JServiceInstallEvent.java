/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceInstallListener.class)
public class JServiceInstallEvent extends JYouAPPEvent<JServiceInstallEvent> {

	private static final long serialVersionUID = -4047669203423811947L;
	
	private final Class<?> serviceClass;
	
	public JServiceInstallEvent(Object source,Class<?> serviceClass) {
		super(source);
		this.serviceClass=serviceClass;
	}

	public JServiceInstallEvent(Object source,int priority ,Class<?> serviceClass) {
		super(source,priority);
		this.serviceClass=serviceClass;
	}

	public Class<?> getServiceClass() {
		return serviceClass;
	}
	
}
