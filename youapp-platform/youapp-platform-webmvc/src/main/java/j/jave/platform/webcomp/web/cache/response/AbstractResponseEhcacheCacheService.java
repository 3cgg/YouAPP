/**
 * 
 */
package j.jave.platform.webcomp.web.cache.response;

import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;
import j.jave.platform.sps.support.ehcache.subhub.EhcacheDelegateService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

/**
 * @author J
 */
public abstract class AbstractResponseEhcacheCacheService 
		implements ResponseEhcacheCacheService, 
			JEhcacheServiceAware,ResponseCacheRefreshListener{
	/**
	 * cache service . 
	 */
	private EhcacheDelegateService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheDelegateService.class);;
	
	@Override
	public EhcacheDelegateService getEhcacheService() {
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheDelegateService) ehcacheService;
	}

	@Override
	public boolean isNeedCache(String path) {
		return false;
	}

	@Override
	public void add(ResponseCacheModel requestResource) {
		getEhcacheService().put(requestResource.getPath(), requestResource);
	}

	@Override
	public void remove(ResponseCacheModel requestResource) {
		getEhcacheService().remove(requestResource.getPath());
	}

	@Override
	public ResponseCacheModel get(String path) {
		return (ResponseCacheModel) getEhcacheService().get(path);
	}
	
}
