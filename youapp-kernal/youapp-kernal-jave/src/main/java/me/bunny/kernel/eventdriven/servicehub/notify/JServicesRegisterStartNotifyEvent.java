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
@JListenerOnEvent(name=JServicesRegisterStartNotifyListener.class)
public class JServicesRegisterStartNotifyEvent extends JYouAPPEvent<JServicesRegisterStartNotifyEvent> {
	
	private final Date time=new Date();
	
	public JServicesRegisterStartNotifyEvent(Object source) {
		super(source);
	}

	public JServicesRegisterStartNotifyEvent(Object source,int priority) {
		super(source,priority);
	}
	
	public Date getTime() {
		return time;
	}
	
}
