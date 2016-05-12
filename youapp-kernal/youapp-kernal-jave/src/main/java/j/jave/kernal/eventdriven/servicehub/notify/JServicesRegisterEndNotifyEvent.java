/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServicesRegisterEndNotifyListener.class)
public class JServicesRegisterEndNotifyEvent extends JAPPEvent<JServicesRegisterEndNotifyEvent> {
	
	public JServicesRegisterEndNotifyEvent(Object source) {
		super(source);
	}

	public JServicesRegisterEndNotifyEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
