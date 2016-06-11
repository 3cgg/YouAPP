/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

import java.util.Date;

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
