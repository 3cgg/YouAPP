/**
 * 
 */
package j.jave.kernal.eventdriven.servicehub.eventlistener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JServiceHubInitializedListener.class)
public class JServiceHubInitializedEvent extends JAPPEvent<JServiceHubInitializedEvent> {
	
	private final JConfiguration configuration;
	
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

	
}
