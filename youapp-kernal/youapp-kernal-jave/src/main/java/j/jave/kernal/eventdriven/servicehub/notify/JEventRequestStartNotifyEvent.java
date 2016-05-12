/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JEventRequestStartNotifyListener.class)
public class JEventRequestStartNotifyEvent extends JAPPEvent<JEventRequestStartNotifyEvent> {
	
	private final JAPPEvent<?> event;
	
	public JEventRequestStartNotifyEvent(Object source,JAPPEvent<?> event) {
		super(source);
		this.event=event;
	}

	public JEventRequestStartNotifyEvent(Object source,int priority ,JAPPEvent<?> event) {
		super(source,priority);
		this.event=event;
	}
	
	public JAPPEvent<?> getEvent() {
		return event;
	}
	
}
