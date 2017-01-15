package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel._c.service.JService;
import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JJarFileProcessorServiceFactory
extends JServiceFactorySupport<JJarFileProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JJarFileProcessorService> ,JService
{

	@Override
	public JJarFileProcessorService getSchemeProcessorService() {
		return new JJarFileProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.JAR.getValue().equals(uri.getScheme())
				;
	}


	@Override
	protected JJarFileProcessorServiceFactory doGetService() {
		return this;
	}
	

}
