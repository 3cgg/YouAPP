package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

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
