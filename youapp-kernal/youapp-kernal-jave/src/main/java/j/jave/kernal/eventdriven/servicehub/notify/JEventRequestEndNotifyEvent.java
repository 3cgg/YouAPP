/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.notify;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

import java.util.Date;

/**
 * @author J
 */
@JListenerOnEvent(name=JEventRequestEndNotifyListener.class)
public class JEventRequestEndNotifyEvent extends JAPPEvent<JEventRequestEndNotifyEvent> {
	
	private final JAPPEvent<?> event;
	
	private final Date time=new Date();
	
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
	
	public Date getTime() {
		return time;
	}
	
}