/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.memcached.subhub;

import j.jave.kernal.memcached.DefaultMemcachedServiceConfiguration;
import j.jave.kernal.memcached.JDefaultMemcachedDisService;
import j.jave.kernal.memcached.JMemcachedDisServiceAware;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;

@Deprecated
public class MemcachedWithSpringConfigServiceFactory extends SpringServiceFactorySupport<MemcachedWithSpringConfigService> {
	
	@Autowired(required=false)
	private DefaultMemcachedServiceConfiguration defaultMemcachedServiceConfiguration;
	
	private MemcachedWithSpringConfigService memcachedService;
	
	private Object sync=new Object();
	
	@Override
	public MemcachedWithSpringConfigService getService() {
		
		if(memcachedService==null){
			synchronized (sync) {
				if(memcachedService==null){
					JMemcachedDisServiceAware memcachedService=  (JMemcachedDisServiceAware) getBeanByName("defaultMemcachedServiceImpl"); 
					if(defaultMemcachedServiceConfiguration!=null){
						JDefaultMemcachedDisService defaultMemcachedDisService=
								new JDefaultMemcachedDisService(defaultMemcachedServiceConfiguration.getStoreAddes(),
										defaultMemcachedServiceConfiguration.getBackupAddes());
						memcachedService.setMemcachedDisService(defaultMemcachedDisService);
					}
					else{
						LOGGER.warn("memcach with spring configuration is not configured, so that is off.");
					}
					this.memcachedService=(MemcachedWithSpringConfigService) memcachedService;
				}
			}
		}
		return memcachedService;
	}
	
	@Override
	public String getName() {
		return "Spring Memcache provider.";
	}
}
