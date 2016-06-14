/**
 * 
 */
package j.jave.platform.basicsupportcomp.support.memcached.subhub;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.reflect.JClassUtils;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.basicsupportcomp.BasicSupportCompProperties;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service(value="j.jave.platform.basicsupportcomp.support.memcached.subhub.MemcachedDelegateServiceFactory")
public class MemcachedDelegateServiceFactory extends SpringServiceFactorySupport<MemcachedDelegateService> {
	
	@Autowired(required=false)
	private MemcachedDelegateServiceProvider delegateServiceProvider;
	
	@Autowired
	@Qualifier(DefaultMemcachedService.BEAN_NAME)
	private MemcachedDelegateService defaultMemcachedDelegateService;
	
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
					else {
						String config=JConfiguration.get().getString(
								BasicSupportCompProperties.YOUAPP_MEMECACHE_MEMORY_INSTEAD_OF_SERVICE);
						if(JStringUtils.isNotNullOrEmpty(config)){
							try {
								instance=(MemcachedDelegateService) JClassUtils.load(config).newInstance();
							} catch (InstantiationException
									| IllegalAccessException e) {
								throw new JInitializationException(e);
							}
						}
						else{
							// use default
							instance=defaultMemcachedDelegateService;
						}
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
