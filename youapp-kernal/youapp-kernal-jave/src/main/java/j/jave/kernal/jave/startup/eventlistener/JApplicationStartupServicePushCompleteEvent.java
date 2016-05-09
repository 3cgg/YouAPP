/**
 * 
 */
package j.jave.kernal.jave.startup.eventlistener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JApplicationStartupServicePushCompleteListener.class)
public class JApplicationStartupServicePushCompleteEvent extends JAPPEvent<JApplicationStartupServicePushCompleteEvent> {
	
	private final JConfiguration configuration;
	
	public JApplicationStartupServicePushCompleteEvent(Object source,JConfiguration configuration) {
		super(source);
		this.configuration=configuration;
	}

	public JApplicationStartupServicePushCompleteEvent(Object source,int priority ,JConfiguration configuration) {
		super(source,priority);
		this.configuration=configuration;
	}
	
	public JConfiguration getConfiguration() {
		return configuration;
	}

	
}
