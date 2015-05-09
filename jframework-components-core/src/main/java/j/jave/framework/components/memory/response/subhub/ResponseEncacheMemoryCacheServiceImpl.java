/**
 * 
 */
package j.jave.framework.components.memory.response.subhub;

import j.jave.framework.components.memory.ResourceCachedRefreshEvent;
import j.jave.framework.components.resource.model.Resource;
import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.components.resource.service.ResourceService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheService;
import j.jave.framework.components.support.ehcache.subhub.EhcacheServiceSupport;
import j.jave.framework.io.memory.JDynamicMemoryCacheIO;
import j.jave.framework.io.memory.JStaticMemoryCacheIO;
import j.jave.framework.servicehub.JServiceHubDelegate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author J
 */
@Service(value="responseEncacheMemoryCacheServiceImpl")
public class ResponseEncacheMemoryCacheServiceImpl implements ResponseEncacheMemoryCacheService, JDynamicMemoryCacheIO,EhcacheServiceSupport ,JStaticMemoryCacheIO{

	/**
	 * cache service . 
	 */
	private EhcacheService ehcacheService=null;
	
	@Autowired
	private ResourceService resourceService;
	
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
		List<Resource> resources= resourceService.getResources();
		if(resources!=null){
			for(int i=0;i<resources.size();i++){
				Resource resource=resources.get(i);
				ResourceExtend resourceExtend=resource.getResourceExtend();
				if(resourceExtend!=null){
					if(Y.equals(resource.getResourceExtend().getCached())){
						map.put(resource.getUrl(), Y);
					}
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
