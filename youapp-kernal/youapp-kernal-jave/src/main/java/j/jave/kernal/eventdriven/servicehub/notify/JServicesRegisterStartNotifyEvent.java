/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServicesRegisterStartNotifyListener.class)
public class JServicesRegisterStartNotifyEvent extends JAPPEvent<JServicesRegisterStartNotifyEvent> {
	
	public JServicesRegisterStartNotifyEvent(Object source) {
		super(source);
	}

	public JServicesRegisterStartNotifyEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
