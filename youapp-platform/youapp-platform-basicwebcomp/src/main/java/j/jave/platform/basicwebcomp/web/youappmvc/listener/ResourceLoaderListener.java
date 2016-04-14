package j.jave.platform.basicwebcomp.web.youappmvc.listener;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.support.resourceuri.InitialResource;
import j.jave.platform.basicwebcomp.web.cache.resource.ResourceCacheServiceSupportUtil;

import java.util.Set;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service
public class ResourceLoaderListener implements ApplicationListener<ContextRefreshedEvent> {
	
	private static final JLogger LOGGER=JLoggerFactory.getLogger(ResourceLoaderListener.class);
	
	@Override
	public void onApplicationEvent(ContextRefreshedEvent event) {
		try{
			JConfiguration configuration=JConfiguration.get();
			Set<InitialResource> initialResources=  ResourceCacheServiceSupportUtil.getInitialResources(configuration);
			for(InitialResource initialResource:initialResources){
				initialResource.initResource(configuration);
			}
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
	}

}