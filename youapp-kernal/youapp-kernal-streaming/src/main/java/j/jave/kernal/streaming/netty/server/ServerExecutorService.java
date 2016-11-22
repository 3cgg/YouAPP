package j.jave.kernal.streaming.netty.server;

import io.netty.channel.ChannelHandlerContext;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.streaming.netty.controller.ControllerExecutorService;
import j.jave.kernal.streaming.netty.controller.FastMessageMeta;

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
	
	
	public Object execute(FastMessageMeta fastMessageMeta,ChannelHandlerContext ctx,Object httpObject) throws Exception{
		return controllerExecutorService.execute(fastMessageMeta);
	}
	
	
	

	
	
}
