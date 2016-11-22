/**
 * 
 */
package j.jave.kernal.streaming.netty.server;

import io.netty.channel.ChannelHandlerContext;
import j.jave.kernal.eventdriven.servicehub.JListenerOnEvent;
import j.jave.kernal.eventdriven.servicehub.JYouAPPEvent;
import j.jave.kernal.streaming.netty.controller.FastMessageMeta;

/**
 * @author J
 */
@JListenerOnEvent(name=AsyncRequestExecutingListener.class)
public class AsyncRequestExecutingEvent extends JYouAPPEvent<AsyncRequestExecutingEvent> {
	
	private FastMessageMeta fastMessageMeta;
	
	private ChannelHandlerContext ctx;
	
	private Object httpObject;
	
	public AsyncRequestExecutingEvent(Object source) {
		super(source);
	}

	public AsyncRequestExecutingEvent(Object source,int priority) {
		super(source,priority);
	}
	
	public FastMessageMeta getFastMessageMeta() {
		return fastMessageMeta;
	}

	public ChannelHandlerContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelHandlerContext ctx) {
		this.ctx = ctx;
	}

	public Object getHttpObject() {
		return httpObject;
	}

	public void setHttpObject(Object httpObject) {
		this.httpObject = httpObject;
	}

	public void setFastMessageMeta(FastMessageMeta fastMessageMeta) {
		this.fastMessageMeta = fastMessageMeta;
	}
	
}
