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
@JListenerOnEvent(name=JEventRequestStartNotifyListener.class)
public class JEventRequestStartNotifyEvent extends JYouAPPEvent<JEventRequestStartNotifyEvent> {
	
	private final JYouAPPEvent<?> event;
	
	private final Date time=new Date();
	
	public JEventRequestStartNotifyEvent(Object source,JYouAPPEvent<?> event) {
		super(source);
		this.event=event;
	}

	public JEventRequestStartNotifyEvent(Object source,int priority ,JYouAPPEvent<?> event) {
		super(source,priority);
		this.event=event;
	}
	
	public JYouAPPEvent<?> getEvent() {
		return event;
	}
	
	public Date getTime() {
		return time;
	}
}
