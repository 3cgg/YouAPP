/**
 * 
 */
package me.bunny.app._c._web.web.cache.resource.weburl;

import me.bunny.app._c._web.web.cache.resource.ResourceCacheServiceSupport;
import me.bunny.app._c._web.web.cache.response.ResponseEhcacheCacheService;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel._c.support.resourceuri.IdentifierGenerator;
import me.bunny.kernel._c.support.resourceuri.ResourceCacheRefreshEvent;
import me.bunny.kernel._c.support.resourceuri.ResourceCacheRefreshListener;
import me.bunny.kernel._c.support.resourceuri.ResourceCacheService;
import me.bunny.kernel._c.support.resourceuri.ResourceCacheServiceGetEvent;
import me.bunny.kernel._c.support.resourceuri.SimpleStringIdentifierGenerator;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * the implementation expose those resources ,
 * that can be controlled to turn on or off the page cached function.
 * @author J
 * @see ResourceCachedService
 * @see ResponseEhcacheCacheService
 * @see MemoryCachedService
 */
@Service(value=WebRequestURLCacheServiceImpl.BEAN_NAME)
public class WebRequestURLCacheServiceImpl extends ResourceCacheServiceSupport<WebRequestURLCacheModel,Object> 
	implements WebRequestURLCacheService,
	ResourceCacheRefreshListener,WebRequestURLCacheRefreshListener{
	
	public static final String BEAN_NAME="default-webRequestURLCacheServiceImpl";
	
	@Autowired(required=false)
	private WebRequestURLCacheModelService webRequestURLCacheModelService;

	@Override
	public ResourceCacheService trigger(ResourceCacheServiceGetEvent event) {
		return this;
	}
	
	@Override
	public void initResource(JConfiguration configuration) {
		List<? extends WebRequestURLCacheModel> resources= webRequestURLCacheModelService.getResourceCacheModels();
		if(resources!=null){
			for(int i=0;i<resources.size();i++){
				WebRequestURLCacheModel resourceCacheModel=resources.get(i);
				set(resourceCacheModel.getUri(), resourceCacheModel);
			}
		}
	}

	@Override
	public Object trigger(ResourceCacheRefreshEvent event) {
		return set();
	}

	@Override
	public boolean isNeedCache(String path) {
		WebRequestURLCacheModel cacheModel=get(path);
		if(cacheModel==null) return false;
		return cacheModel.isCached();
	}
	
	private static SimpleStringIdentifierGenerator simpleStringIdentifierGenerator=new SimpleStringIdentifierGenerator(){
		public String namespace() {
			return "/webrequseturl/";
		};
	};
	
	@Override
	public IdentifierGenerator generator() {
		return simpleStringIdentifierGenerator;
	}

	@Override
	public Object trigger(WebRequestURLCacheRefreshEvent event) {
		initResource(JConfiguration.get());
		return true;
	}
}
