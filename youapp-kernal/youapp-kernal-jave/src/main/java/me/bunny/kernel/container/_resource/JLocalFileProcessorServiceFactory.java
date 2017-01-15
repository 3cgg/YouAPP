package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;

public class JLocalFileProcessorServiceFactory
extends JServiceFactorySupport<JLocalFileProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JLocalFileProcessorService> ,JService
{

	@Override
	public JLocalFileProcessorService getSchemeProcessorService() {
		return new JLocalFileProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.FILE.getValue().equals(uri.getScheme())
				;
	}


	@Override
	protected JLocalFileProcessorServiceFactory doGetService() {
		return this;
	}

}
