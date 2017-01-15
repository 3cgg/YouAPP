/**
 * 
 */
package me.bunny.kernel.dataexchange.channel;

import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=JMessageSendingListener.class)
public class JMessageSendingEvent extends JYouAPPEvent<JMessageSendingEvent> {
	
	private final JResponseFuture responseFuture;
	
	public JMessageSendingEvent(Object source,JResponseFuture responseFuture) {
		super(source);
		this.responseFuture=responseFuture;
	}

	public JMessageSendingEvent(Object source,int priority ,JResponseFuture responseFuture) {
		super(source,priority);
		this.responseFuture=responseFuture;
	}
	
	public JResponseFuture getResponseFuture() {
		return responseFuture;
	}
	
}
