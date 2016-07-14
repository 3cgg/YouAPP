/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.listener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

import java.util.Date;

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
