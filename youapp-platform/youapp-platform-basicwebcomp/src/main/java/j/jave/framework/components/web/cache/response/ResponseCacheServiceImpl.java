/**
 * 
 */
package j.jave.framework.components.web.cache.response;

import j.jave.framework.commons.ehcache.JEhcacheService;
import j.jave.framework.commons.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.framework.commons.io.memory.JSingleDynamicMemoryCacheIO;
import j.jave.framework.commons.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.web.cache.resource.ResourceCacheModel;
import j.jave.framework.components.web.cache.resource.ResourceCacheService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="j.jave.framework.components.resource.cache.ResponseCacheServiceImpl")
public class ResponseCacheServiceImpl 
	extends ResponseEhcacheMemoryCacheServiceImpl
		implements JSingleDynamicMemoryCacheIO,
			JSingleStaticMemoryCacheIO{

	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=null;
	
	@Autowired
	private ResourceCacheService resourceCacheService;
	
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
		set();
		return "Refresh resource successfully.";
	}

	
	private static final String RESOURCE_CACHED_KEY="j.jave.framework.components.memory.response.subhub.ResponseEncacheMemoryCacheService";

	private static final String Y="Y";
	
	@Override
	public Map<String, String> set() {
		Map<String, String> map=new HashMap<String, String>();
		List<ResourceCacheModel> resources= resourceCacheService.getResourceCacheModel();
		if(resources!=null){
			for(int i=0;i<resources.size();i++){
				ResourceExtend resourceCached=(ResourceExtend) resources.get(i);
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
