/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceMonitorListener.class)
public class JServiceMonitorEvent extends JAPPEvent<JServiceMonitorEvent> {
	
	public JServiceMonitorEvent(Object source) {
		super(source);
	}

	public JServiceMonitorEvent(Object source,int priority) {
		super(source,priority);
	}
	
}
