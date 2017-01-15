package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;

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
