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
