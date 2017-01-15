package me.bunny.kernel.container._resource;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import me.bunny.kernel.container.JExecutableURIUtil;
import me.bunny.kernel.container.JResourceContainerConfig;
import me.bunny.kernel.container.JExecutableURIUtil.Type;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.exception.JOperationNotSupportedException;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.support._resource.JResourceNotSupportedException;

public class JResourceAccessService
extends JServiceFactorySupport<JResourceAccessService>
implements JService
{

	private JResourceURIParserService parser=JServiceHubDelegate.get()
			.getService(this, JResourceURIParserService.class);
	
	
	private List<JResourceProcessorServiceFactory<?>> processorServiceFactories
	=new ArrayList<JResourceProcessorServiceFactory<?>>(8);
	{
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JClassPathProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JLocalFileProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JJarFileProcessorServiceFactory.class));
		processorServiceFactories.add(JServiceHubDelegate.get().getService(this, JHttpURLConnectionProcessorServiceFactory	.class));
	
	}
	
	/**
	 * convenience to {@link #execute(URI, Object, Type.GET)}
	 * @param uri
	 * @param object
	 * @return
	 */
	public <T> T execute(URI uri,Object object) throws Exception{
		return execute(uri, object, Type.GET);
	}
	
	/**
	 * assume the URI is a wrapped resource via {@code JExecutableURIUtil#isWrapped(URI)} ,
	 * if no sure , we attempt to parse to the wrapped URI. 
	 * @param uri
	 * @param object
	 * @param type
	 * @return
	 * @throws Exception
	 * @throws JResourceNotSupportedException
	 */
	@SuppressWarnings("unchecked")
	public <T> T execute(URI uri,Object object,Type type) throws Exception{
		URI wrappedURI=uri;
		if(!JExecutableURIUtil.isWrapped(wrappedURI)){
			wrappedURI=parser.parse(uri, JResourceContainerConfig.DEFAULT_UNIQUE, type);
		}
		
		for(JResourceProcessorServiceFactory<?> factory:processorServiceFactories ){
			if(factory.accept(wrappedURI)){
				return (T) factory.getSchemeProcessorService().process(wrappedURI,object);
			}
		}
		throw new JOperationNotSupportedException("the resource cannot be processed. : "+wrappedURI.toString());
	}
	
	@Override
	protected JResourceAccessService doGetService() {
		return this;
	}
	
	
}
