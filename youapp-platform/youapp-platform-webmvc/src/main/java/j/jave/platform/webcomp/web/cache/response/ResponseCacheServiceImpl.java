/**
 * 
 */
package j.jave.platform.webcomp.web.cache.response;

import j.jave.kernal.ehcache.JEhcacheService;
import j.jave.platform.webcomp.web.cache.resource.weburl.WebRequestURLCacheService;
import me.bunny.app._c.sps.support.ehcache.subhub.EhcacheDelegateService;
import me.bunny.kernel._c.io.memory.JSingleDynamicMemoryCacheIO;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value=ResponseCacheServiceImpl.BEAN_NAME)
public class ResponseCacheServiceImpl 
	extends AbstractResponseEhcacheCacheService
		implements JSingleDynamicMemoryCacheIO<ResponseCacheModel>{

	public static final String BEAN_NAME="default-responseCacheServiceImpl";
	
	
	/**
	 * cache service . 
	 */
	private EhcacheDelegateService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheDelegateService.class);;
	
	@Autowired
	private WebRequestURLCacheService webRequestURLCacheService;
	
	@Override
	public EhcacheDelegateService getEhcacheService() {
		return ehcacheService;
	}
	
	@Override
	public void setEhcacheService(JEhcacheService ehcacheService) {
		this.ehcacheService=(EhcacheDelegateService) ehcacheService;
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
