/**
 * 
 */
package me.bunny.modular._p.streaming.netty.server;

import io.netty.channel.ChannelHandlerContext;
import me.bunny.kernel.eventdriven.servicehub.JListenerOnEvent;
import me.bunny.kernel.eventdriven.servicehub.JYouAPPEvent;
import me.bunny.modular._p.streaming.netty.msg.RPCFullMessage;

/**
 * @author J
 */
@JListenerOnEvent(name=AsyncRequestExecutingListener.class)
public class AsyncRequestExecutingEvent extends JYouAPPEvent<AsyncRequestExecutingEvent> {
	
	private RPCFullMessage rpcFullMessage;
	
	private ChannelHandlerContext ctx;
	
	private Object httpObject;
	
	public AsyncRequestExecutingEvent(Object source) {
		super(source);
	}

	public AsyncRequestExecutingEvent(Object source,int priority) {
		super(source,priority);
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

	public RPCFullMessage getRpcFullMessage() {
		return rpcFullMessage;
	}

	public void setRpcFullMessage(RPCFullMessage rpcFullMessage) {
		this.rpcFullMessage = rpcFullMessage;
	}

	
	
}
