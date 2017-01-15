/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.monitor;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceHubMonitorListener.class)
public class JServiceHubMonitorEvent extends JYouAPPEvent<JServiceHubMonitorEvent> {
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
