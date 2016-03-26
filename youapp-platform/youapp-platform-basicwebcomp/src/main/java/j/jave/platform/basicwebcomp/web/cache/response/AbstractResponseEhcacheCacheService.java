/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.kernal.ehcache.JEhcacheServiceAware;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicsupportcomp.support.ehcache.subhub.EhcacheService;

/**
 * @author J
 */
public abstract class AbstractResponseEhcacheCacheService 
		implements ResponseEhcacheCacheService, 
			JEhcacheServiceAware,ResponseCacheRefreshListener{
	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);;
	
	@Override
	public EhcacheService getEhcacheService() {
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheService) ehcacheService;
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
