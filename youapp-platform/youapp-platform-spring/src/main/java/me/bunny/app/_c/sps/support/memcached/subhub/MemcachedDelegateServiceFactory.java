/**
 * 
 */
package me.bunny.app._c.sps.support.memcached.subhub;

import me.bunny.app._c.sps.core.servicehub.SpringServiceFactorySupport;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="MemcachedDelegateServiceFactory")
public class MemcachedDelegateServiceFactory extends SpringServiceFactorySupport<MemcachedDelegateService> {
	
	@Autowired
	@Qualifier(DefaultMemcachedDelegateService.BEAN_NAME)
	private MemcachedDelegateService defaultService;
	
	private MemcachedDelegateService inMemoryService
	=JServiceHubDelegate.get().getService(this, MemoryInsteadOfMemcachedDelegateService.class);
	
	private Object sync=new Object();
	
	private MemcachedDelegateService instance;
	
	@Override
	public MemcachedDelegateService getService() {
		if(instance==null){ 
			synchronized (sync) {
				if(instance==null){
					try{
						//TEST IF THE CACHE IS ACTIVE.
						defaultService.contains("test");
						instance=defaultService;
						LOGGER.info("-----------memcache is active ------ ");
					}catch(Exception e){
						LOGGER.error(e.getMessage(), e);
						instance=inMemoryService;
						LOGGER.info("[Memcache] use in memory instead of .");
					}
				}
			}
		}
		return instance;
	}
	
	@Override
	public String getName() {
		return "memcached-cache provider.";
	}
}
