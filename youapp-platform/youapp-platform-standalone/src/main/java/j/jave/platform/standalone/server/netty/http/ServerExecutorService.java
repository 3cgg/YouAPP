package j.jave.platform.standalone.server.netty.http;

import j.jave.platform.standalone.server.controller.ControllerExecutorService;
import me.bunny.kernel._c.base64.JBase64;
import me.bunny.kernel._c.base64.JBase64FactoryProvider;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.dataexchange.model.MessageMeta;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

public class ServerExecutorService 
extends JServiceFactorySupport<ServerExecutorService>
implements JService
{
	
	@Override
	protected ServerExecutorService doGetService() {
		return this;
	}
	
	private MessaageMetaExtractService extractService=JServiceHubDelegate.get()
			.getService(this, MessaageMetaExtractService.class);
	
	private ControllerExecutorService controllerExecutorService=JServiceHubDelegate.get()
			.getService(this, ControllerExecutorService.class);
	
	protected JBase64 base64Service=JBase64FactoryProvider.getBase64Factory().getBase64();
	
	public Object execute(byte[] bytes) throws Exception{
		MessageMeta messageMeta=extractService.parse(bytes);
		return controllerExecutorService.execute(messageMeta);
	}
	
	
	

	
	
}
