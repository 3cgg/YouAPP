package j.jave.kernal.container._resource;

import j.jave.kernal.container.JScheme;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

import java.net.URI;

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
