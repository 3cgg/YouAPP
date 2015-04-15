/**
 * 
 */
package j.jave.framework.components.support.memcached.subhub;

import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;
import j.jave.framework.memcached.JDefaultMemcachedDisService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Administrator
 *
 */
@Service(value="memcachedServiceFactory")
public class MemcachedServiceFactory extends SpringServiceFactorySupport<MemcachedService> {
	
	public MemcachedServiceFactory() {
		super(MemcachedService.class);
	}
	
	@Autowired(required=false)
	private DefaultMemcachedServiceConfiguration defaultMemcachedServiceConfiguration;
	
	@Override
	public MemcachedService getService() {
		DefaultMemcachedServiceImpl memcachedService=  (DefaultMemcachedServiceImpl) getBeanByName("defaultMemcachedServiceImpl"); 
		if(defaultMemcachedServiceConfiguration!=null){
			JDefaultMemcachedDisService defaultMemcachedDisService=
					new JDefaultMemcachedDisService(defaultMemcachedServiceConfiguration.getStoreAddes(),
							defaultMemcachedServiceConfiguration.getBackupAddes());
			memcachedService.setDefaultMemcachedDisService(defaultMemcachedDisService);
		}
		return memcachedService;
	}
	
	@Override
	public String getName() {
		return "Cache provider";
	}
}
