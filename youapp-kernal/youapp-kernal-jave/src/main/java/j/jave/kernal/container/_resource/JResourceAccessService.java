package j.jave.kernal.container._resource;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.service.JService;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class JResourceAccessService
extends JServiceFactorySupport<JResourceAccessService>
implements JService
{

	private List<JResourceProcessorServiceFactory<?>> processorServiceFactories
	=new ArrayList<JResourceProcessorServiceFactory<?>>(8);
	{
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JClassPathProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JLocalFileProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JJarFileProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JHttpURLConnectionProcessorServiceFactory	.class));
	
	}
	
	@SuppressWarnings("unchecked")
	public <T> T execute(URI uri,Object object){
		for(JResourceProcessorServiceFactory<?> factory:processorServiceFactories ){
			if(factory.accept(uri)){
				return (T) factory.getSchemeProcessorService().process(uri,object);
			}
		}
		throw new JOperationNotSupportedException("the resource cannot be processed. : "+uri.toString());
	}
	
	
	@Override
	protected JResourceAccessService doGetService() {
		return this;
	}
	
	
}
