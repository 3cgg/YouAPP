/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

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
