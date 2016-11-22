/**
 * 
 */
package j.jave.kernal.streaming.netty.controller;

import java.util.Date;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

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
