/**
 * 
 */
package j.jave.kernal.dataexchange.channel;

import j.jave.kernal.eventdriven.servicehub.JAPPEvent;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=ObjectTransModelSendingListener.class)
public class ObjectTransModelSendingEvent extends JAPPEvent<ObjectTransModelSendingEvent> {
	
	private final ResponseFuture responseFuture;
	
	public ObjectTransModelSendingEvent(Object source,ResponseFuture responseFuture) {
		super(source);
		this.responseFuture=responseFuture;
	}

	public ObjectTransModelSendingEvent(Object source,int priority ,ResponseFuture responseFuture) {
		super(source,priority);
		this.responseFuture=responseFuture;
	}
	
	public ResponseFuture getResponseFuture() {
		return responseFuture;
	}
	
}
