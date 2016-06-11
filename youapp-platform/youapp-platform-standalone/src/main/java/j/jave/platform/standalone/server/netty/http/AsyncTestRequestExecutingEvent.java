/**
 * 
 */
package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;

/**
 * @author J
 */
@JListenerOnEvent(name=AsyncTestRequestExecutingListener.class)
public class AsyncTestRequestExecutingEvent extends JYouAPPEvent<AsyncTestRequestExecutingEvent> {
	
	private final RequestContext requestContext;
	
	public AsyncTestRequestExecutingEvent(Object source,RequestContext requestContext) {
		super(source);
		this.requestContext=requestContext;
	}

	public AsyncTestRequestExecutingEvent(Object source,int priority ,RequestContext requestContext) {
		super(source,priority);
		this.requestContext=requestContext;
	}
	
	public RequestContext getRequestContext() {
		return requestContext;
	}
	
}
