/**
 * 
 */
package j.jave.platform.basicwebcomp.web.cache.resource.weburl;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheRefreshEvent;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheRefreshListener;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheService;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheServiceGetEvent;
import j.jave.kernal.jave.support.resourceuri.SimpleStringIdentifierGenerator;
import j.jave.platform.basicwebcomp.web.cache.resource.ResourceCacheServiceSupport;
import j.jave.platform.basicwebcomp.web.cache.response.ResponseEhcacheCacheService;

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
@Service(value="j.jave.platform.basicwebcomp.web.cache.resource.weburl.WebRequestURLCacheServiceImpl")
public class WebRequestURLCacheServiceImpl extends ResourceCacheServiceSupport<WebRequestURLCacheModel,Object> 
	implements WebRequestURLCacheService,
	ResourceCacheRefreshListener,WebRequestURLCacheRefreshListener{
	
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
