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
@JListenerOnEvent(name=JEventRequestEndNotifyListener.class)
public class JEventRequestEndNotifyEvent extends JYouAPPEvent<JEventRequestEndNotifyEvent> {
	
	private final JYouAPPEvent<?> event;
	
	private final Date time=new Date();
	
	public JEventRequestEndNotifyEvent(Object source,JYouAPPEvent<?> event) {
		super(source);
		this.event=event;
	}

	public JEventRequestEndNotifyEvent(Object source,int priority ,JYouAPPEvent<?> event) {
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
