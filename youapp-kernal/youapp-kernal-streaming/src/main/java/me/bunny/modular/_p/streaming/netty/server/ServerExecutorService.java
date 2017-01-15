package me.bunny.modular._p.streaming.netty.server;

import io.netty.channel.ChannelHandlerContext;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.modular._p.streaming.netty.controller.ControllerExecutorService;
import me.bunny.modular._p.streaming.netty.msg.RPCFullMessage;

public class ServerExecutorService 
extends JServiceFactorySupport<ServerExecutorService>
implements JService
{
	
	@Override
	protected ServerExecutorService doGetService() {
		return this;
	}
	
	private ControllerExecutorService controllerExecutorService=JServiceHubDelegate.get()
			.getService(this, ControllerExecutorService.class);
	
	
	public Object execute(RPCFullMessage fastMessageMeta,ChannelHandlerContext ctx,Object httpObject) throws Exception{
		return controllerExecutorService.execute(fastMessageMeta);
	}
	
	
	

	
	
}
