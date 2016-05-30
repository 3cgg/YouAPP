/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=MessageSendingListener.class)
public class MessageSendingEvent extends JAPPEvent<MessageSendingEvent> {
	
	private final ResponseFuture responseFuture;
	
	public MessageSendingEvent(Object source,ResponseFuture responseFuture) {
		super(source);
		this.responseFuture=responseFuture;
	}

	public MessageSendingEvent(Object source,int priority ,ResponseFuture responseFuture) {
		super(source,priority);
		this.responseFuture=responseFuture;
	}
	
	public ResponseFuture getResponseFuture() {
		return responseFuture;
	}
	
}
