/**
 * 
 */
package j.jave.framework.components.web.subhub.resourcecached.response;

import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.memory.JSingleDynamicMemoryCacheIO;
import j.jave.framework.commons.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheServiceSupport;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCached;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedRefreshEvent;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedService;
import j.jave.framework.components.web.subhub.resourcecached.ResourceCachedServiceGetEvent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="responseEncacheMemoryCacheServiceImpl")
public class ResponseEhcacheMemoryCacheServiceImpl implements ResponseEhcacheMemoryCacheService, JSingleDynamicMemoryCacheIO,EhcacheServiceSupport ,JSingleStaticMemoryCacheIO{

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
	public Object set(String key, Object object) {
		return getEhcacheService().put(key, object);
	}

	@Override
	public Object remove(String key) {
		return getEhcacheService().remove(key);
	}

	@Override
	public boolean isNeedCache(String path) {
		Map<String, String> map= get();
		return map.containsKey(path);
	}

	@Override
	public void add(ResponseCachedResource requestResource) {
		getEhcacheService().put(requestResource.getPath(), requestResource);
	}

	@Override
	public void remove(ResponseCachedResource requestResource) {
		getEhcacheService().remove(requestResource.getPath());
	}

	@Override
	public ResponseCachedResource get(String path) {
		return (ResponseCachedResource) getEhcacheService().get(path);
	}

	@Override
	public Object trigger(ResourceCachedRefreshEvent event) {
		set();
		return "Refresh resource successfully.";
	}

	
	private static final String RESOURCE_CACHED_KEY="j.jave.framework.components.memory.response.subhub.ResponseEncacheMemoryCacheService";

	private static final String Y="Y";
	
	@Override
	public Map<String, String> set() {
		Map<String, String> map=new HashMap<String, String>();
		ResourceCachedService cachedService=JServiceHubDelegate.get().addImmediateEvent(new ResourceCachedServiceGetEvent(this), ResourceCachedService.class);
		List<ResourceCached> resources= cachedService.getResourceCached();
		if(resources!=null){
			for(int i=0;i<resources.size();i++){
				ResourceCached resourceCached=resources.get(i);
				if(Y.equals(resourceCached.getCached())){
					map.put(resourceCached.getUrl(), Y);
				}
			}
		}
		getEhcacheService().put(RESOURCE_CACHED_KEY, map);
		return map;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, String> get() {
		Object obj=getEhcacheService().get(RESOURCE_CACHED_KEY);
		if(obj==null){
			obj=set();
		}
		return (Map<String, String>) obj;
	}
	
}
