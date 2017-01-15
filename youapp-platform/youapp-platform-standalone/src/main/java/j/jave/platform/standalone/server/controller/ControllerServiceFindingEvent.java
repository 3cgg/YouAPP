/**
 * 
 */
package j.jave.platform.standalone.server.controller;

import java.util.Date;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=ControllerServiceFindingListener.class)
public class ControllerServiceFindingEvent extends JYouAPPEvent<ControllerServiceFindingEvent> {
	
	private final JConfiguration configuration;
	
	private final Date time=new Date();
	
	public ControllerServiceFindingEvent(Object source,JConfiguration configuration) {
		super(source);
		this.configuration=configuration;
	}

	public ControllerServiceFindingEvent(Object source,int priority ,JConfiguration configuration) {
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
