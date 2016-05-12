/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JEventRequestEndNotifyListener.class)
public class JEventRequestEndNotifyEvent extends JAPPEvent<JEventRequestEndNotifyEvent> {
	
	private final JAPPEvent<?> event;
	
	public JEventRequestEndNotifyEvent(Object source,JAPPEvent<?> event) {
		super(source);
		this.event=event;
	}

	public JEventRequestEndNotifyEvent(Object source,int priority ,JAPPEvent<?> event) {
		super(source,priority);
		this.event=event;
	}
	
	public JAPPEvent<?> getEvent() {
		return event;
	}
	
}
