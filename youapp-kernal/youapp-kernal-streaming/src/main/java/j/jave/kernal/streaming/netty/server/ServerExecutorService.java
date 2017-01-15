package j.jave.kernal.streaming.netty.server;

import io.netty.channel.ChannelHandlerContext;
import j.jave.kernal.streaming.netty.controller.ControllerExecutorService;
import j.jave.kernal.streaming.netty.msg.RPCFullMessage;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.service.JService;

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
