package me.bunny.kernel.container._resource;

import java.net.URI;

import me.bunny.kernel.container.JScheme;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.jave.service.JService;

public class JClassPathProcessorServiceFactory
extends JServiceFactorySupport<JClassPathProcessorServiceFactory>
implements JResourceProcessorServiceFactory<JClassPathProcessorService>,JService 
{

	@Override
	public JClassPathProcessorService getSchemeProcessorService() {
		return new JClassPathProcessorService();
	}

	@Override
	public boolean accept(URI uri) {
		return JScheme.CLASSPATH.getValue().equals(uri.getScheme());
	}
	

	@Override
	protected JClassPathProcessorServiceFactory doGetService() {
		return this;
	}
	
}
