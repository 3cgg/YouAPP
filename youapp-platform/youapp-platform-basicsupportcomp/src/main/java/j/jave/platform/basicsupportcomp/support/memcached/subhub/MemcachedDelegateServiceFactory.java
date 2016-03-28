/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.memcached.subhub;

import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateServiceFactory")
public class MemcachedDelegateServiceFactory extends SpringServiceFactorySupport<MemcachedDelegateService> {
	
	@Autowired(required=false)
	private MemcachedDelegateServiceProvider delegateServiceProvider;
	
	@Autowired
	private MemcachedDelegateService memcachedService;
	
	private Object sync=new Object();
	
	private MemcachedDelegateService instance;
	
	@Override
	public MemcachedDelegateService getService() {
		
		if(instance==null){
			synchronized (sync) {
				if(instance==null){
					
					if(delegateServiceProvider!=null){
						instance=delegateServiceProvider;
					}
					else{
						instance=memcachedService;
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
