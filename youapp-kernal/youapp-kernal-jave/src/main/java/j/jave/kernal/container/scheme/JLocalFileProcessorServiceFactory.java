package j.jave.kernal.container.scheme;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.service.JService;

import java.net.URI;

public class JLocalFileProcessorServiceFactory
extends JServiceFactorySupport<JLocalFileProcessorServiceFactory>
implements JSchemeProcessorServiceFactory<JLocalFileProcessorService> ,JService
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
