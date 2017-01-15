/**
 * 
 */
package me.bunny.kernel.eventdriven.servicehub.listener;

import java.util.Date;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceHubInitializedListener.class)
public class JServiceHubInitializedEvent extends JYouAPPEvent<JServiceHubInitializedEvent> {
	
	private final JConfiguration configuration;
	
	private final Date time=new Date();
	
	public JServiceHubInitializedEvent(Object source,JConfiguration configuration) {
		super(source);
		this.configuration=configuration;
	}

	public JServiceHubInitializedEvent(Object source,int priority ,JConfiguration configuration) {
		super(source,priority);
		this.configuration=configuration;
	}
	
	public JConfiguration getConfiguration() {
		return configuration;
	}

	public Date getTime() {
		return time;
	}
	
}
