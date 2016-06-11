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
