/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.response;

import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.io.memory.JSingleDynamicMemoryCacheIO;
import j.jave.platform.basicsupportcomp.support.ehcache.subhub.EhcacheService;
import j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.platform.basicwebcomp.web.cache.response.ResponseCacheServiceImpl")
public class ResponseCacheServiceImpl 
	extends AbstractResponseEhcacheCacheService
		implements JSingleDynamicMemoryCacheIO<ResponseCacheModel>{

	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);;
	
	@Autowired
	private WebRequestURLCacheService webRequestURLCacheService;
	
	@Override
	public EhcacheService getEhcacheService() {
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheService) ehcacheService;
	}
	
	@Override
	public ResponseCacheModel set(String key, ResponseCacheModel object) {
		return (ResponseCacheModel) ehcacheService.put(key, object);
	}

	@Override
	public ResponseCacheModel remove(String key) {
		return (ResponseCacheModel) getEhcacheService().remove(key);
	}

	@Override
	public boolean isNeedCache(String path) {
		return webRequestURLCacheService.isNeedCache(path);
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

	@Override
	public Object trigger(ResponseCacheRefreshEvent event) {
		return "Refresh resource successfully.";
	}
	
	
	
}
