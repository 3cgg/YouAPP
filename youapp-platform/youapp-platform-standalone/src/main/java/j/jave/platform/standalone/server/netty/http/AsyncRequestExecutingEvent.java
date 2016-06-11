/**
 * 
 */
package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=AsyncRequestExecutingListener.class)
public class AsyncRequestExecutingEvent extends JYouAPPEvent<AsyncRequestExecutingEvent> {
	
	private final RequestContext requestContext;
	
	public AsyncRequestExecutingEvent(Object source,RequestContext requestContext) {
		super(source);
		this.requestContext=requestContext;
	}

	public AsyncRequestExecutingEvent(Object source,int priority ,RequestContext requestContext) {
		super(source,priority);
		this.requestContext=requestContext;
	}
	
	public RequestContext getRequestContext() {
		return requestContext;
	}
	
}
