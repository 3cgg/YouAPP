package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;

public class JHttpURLConnectionProcessorServiceFactory
extends JServiceFactorySupport<JHttpURLConnectionProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JHttpURLConnectionProcessorService> 
,JService{

	@Override
	public JHttpURLConnectionProcessorService getSchemeProcessorService() {
		return new JHttpURLConnectionProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.HTTP.getValue().equals(uri.getScheme())
				||JScheme.HTTPS.getValue().equals(uri.getScheme())
				;
	}


	@Override
	protected JHttpURLConnectionProcessorServiceFactory doGetService() {
		return this;
	}
	

}
