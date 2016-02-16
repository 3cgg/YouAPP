/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.monitor;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceHubMonitorListener.class)
public class JServiceHubMonitorEvent extends JAPPEvent<JServiceHubMonitorEvent> {
	private boolean refresh;
	public JServiceHubMonitorEvent(Object source) {
		super(source);
	}
	public JServiceHubMonitorEvent(Object source,int priority) {
		super(source,priority);
	}
	public boolean isRefresh() {
		return refresh;
	}
	public void setRefresh(boolean refresh) {
		this.refresh = refresh;
	}
	
}
