package j.jave.platform.webcomp.web.cache.resource;

import j.jave.platform.sps.support.ehcache.subhub.EhcacheDelegateService;
import j.jave.platform.webcomp.WebCompProperties;
import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;
import me.bunny.kernel.jave.exception.JOperationNotSupportedException;
import me.bunny.kernel.jave.io.memory.JSingleStaticMemoryCacheIO;
import me.bunny.kernel.jave.reflect.JClassUtils;
import me.bunny.kernel.jave.service.JCacheService;
import me.bunny.kernel.jave.support.resourceuri.DefaultIdentifierGenerator;
import me.bunny.kernel.jave.support.resourceuri.IdentifierGenerator;
import me.bunny.kernel.jave.support.resourceuri.InitialResource;
import me.bunny.kernel.jave.support.resourceuri.ResourceCacheRefreshEvent;
import me.bunny.kernel.jave.support.resourceuri.ResourceCacheService;

import java.util.HashSet;
import java.util.Set;

public abstract class ResourceCacheServiceSupport<T,M> implements ResourceCacheService<T>,
JSingleStaticMemoryCacheIO<M>,InitialResource{

	private JCacheService cacheService=null;
	{
		String cacheImpl=JConfiguration.get().getString(WebCompProperties.YOUAPPMVC_RESOURCE_CACHE_SERVICE_INTERFACE, EhcacheDelegateService.class.getName());
		cacheService=JServiceHubDelegate.get().getService(this, JClassUtils.load(cacheImpl));
	}
	private static DefaultIdentifierGenerator defaultIdentifierGenerator=new DefaultIdentifierGenerator();
	
	private static Set<InitialResource> initialResources=new HashSet<InitialResource>();
	
	static Set<InitialResource> getInitialResources() {
		return initialResources;
	}
	
	public ResourceCacheServiceSupport() {
		initialResources.add(this);
	}
	
	@Override
	public IdentifierGenerator generator() {
		return defaultIdentifierGenerator;
	}

	@Override
	public T set(String key, T object) {
		return (T) cacheService.putNeverExpired(generator().key(key), object);
	}

	@Override
	public T get(String key) {
		return (T) cacheService.get(generator().key(key));
	}
	
	@Override
	public final M set() {
		initResource(JConfiguration.get());
		return null;
	}

	@Override
	public T remove(String key) {
		return (T) cacheService.remove(generator().key(key));
	}
	
	@Override
	public final M get() {
		throw new JOperationNotSupportedException("not supported.");
	}
	
	@Override
	public Object trigger(ResourceCacheRefreshEvent event) {
		initResource(JConfiguration.get());
		return true;
	}
}
