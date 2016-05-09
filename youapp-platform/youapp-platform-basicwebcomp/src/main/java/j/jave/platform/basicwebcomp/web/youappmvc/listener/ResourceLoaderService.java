package j.jave.platform.basicwebcomp.web.youappmvc.listener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.support.resourceuri.InitialResource;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedEvent;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedListener;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.web.cache.resource.ResourceCacheServiceSupportUtil;

import java.util.Set;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service
public class ResourceLoaderService extends SpringServiceFactorySupport<ResourceLoaderService>
implements JService,SpringApplicationContextInitializedListener {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ResourceLoaderService.class);
	
	@Override
	public Object trigger(SpringApplicationContextInitializedEvent event) {
		try{
			JConfiguration configuration=event.getConfiguration()==null?JConfiguration.get():event.getConfiguration();
			Set<InitialResource> initialResources=  ResourceCacheServiceSupportUtil.getInitialResources(configuration);
			for(InitialResource initialResource:initialResources){
				initialResource.initResource(configuration);
			}
			return true;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
	}

}
