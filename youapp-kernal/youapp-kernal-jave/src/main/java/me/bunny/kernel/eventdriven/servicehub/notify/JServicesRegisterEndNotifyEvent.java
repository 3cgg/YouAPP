/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.notify;

import java.util.Date;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServicesRegisterEndNotifyListener.class)
public class JServicesRegisterEndNotifyEvent extends JYouAPPEvent<JServicesRegisterEndNotifyEvent> {
	
	private final Date time=new Date();
	
	public JServicesRegisterEndNotifyEvent(Object source) {
		super(source);
	}

	public JServicesRegisterEndNotifyEvent(Object source,int priority) {
		super(source,priority);
	}

	public Date getTime() {
		return time;
	}
}
