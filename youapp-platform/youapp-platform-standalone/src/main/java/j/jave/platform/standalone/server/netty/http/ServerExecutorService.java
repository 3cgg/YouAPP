package j.jave.platform.standalone.server.netty.http;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.base64.JBase64;
import j.jave.kernal.jave.base64.JBase64FactoryProvider;
import j.jave.kernal.jave.service.JService;
import j.jave.platform.standalone.data.MessageMeta;
import j.jave.platform.standalone.server.controller.ControllerExecutorService;

public class ServerExecutorService 
extends JServiceFactorySupport<ServerExecutorService>
implements JService
{
	
	@Override
	public ServerExecutorService getService() {
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
