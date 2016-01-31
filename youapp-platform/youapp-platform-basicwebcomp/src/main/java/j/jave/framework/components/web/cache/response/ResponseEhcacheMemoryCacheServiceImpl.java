/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.ehcache.JEhcacheService;
import j.jave.framework.commons.ehcache.JEhcacheServiceAware;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;

/**
 * @author J
 */
public abstract class ResponseEhcacheMemoryCacheServiceImpl 
		implements ResponseEhcacheMemoryCacheService, 
			JEhcacheServiceAware,ResponseCacheRefreshListener{
	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=null;
	
	@Override
	public EhcacheService getEhcacheService() {
		if(ehcacheService==null){
			ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);
		}
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
