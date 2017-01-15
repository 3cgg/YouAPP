package j.jave.platform.webcomp.web.youappmvc.listener;

import j.jave.platform.sps.core.servicehub.SpringApplicationContextInitializedEvent;
import j.jave.platform.sps.core.servicehub.SpringApplicationContextInitializedListener;
import j.jave.platform.sps.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.webcomp.web.cache.resource.ResourceCacheServiceSupportUtil;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.support.resourceuri.InitialResource;

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
