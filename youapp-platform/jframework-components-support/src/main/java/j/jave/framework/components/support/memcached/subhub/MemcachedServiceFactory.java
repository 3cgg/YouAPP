/**
 * 
 */
package j.jave.framework.components.support.memcached.subhub;

import j.jave.framework.commons.memcached.JDefaultMemcachedDisService;
import j.jave.framework.commons.memcached.JMemcachedDisServiceAware;
import j.jave.framework.components.core.servicehub.SpringServiceFactorySupport;

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
	
	private MemcachedService memcachedService;
	
	private Object sync=new Object();
	
	@Override
	public MemcachedService getService() {
		
		if(memcachedService==null){
			synchronized (sync) {
				JMemcachedDisServiceAware memcachedService=  (JMemcachedDisServiceAware) getBeanByName("defaultMemcachedServiceImpl"); 
				if(defaultMemcachedServiceConfiguration!=null){
					JDefaultMemcachedDisService defaultMemcachedDisService=
							new JDefaultMemcachedDisService(defaultMemcachedServiceConfiguration.getStoreAddes(),
									defaultMemcachedServiceConfiguration.getBackupAddes());
					memcachedService.setMemcachedDisService(defaultMemcachedDisService);
				}
				this.memcachedService=(MemcachedService) memcachedService;
			}
		}
		return memcachedService;
	}
	
	@Override
	public String getName() {
		return "Cache provider";
	}
}
