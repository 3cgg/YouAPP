package j.jave.platform.basicwebcomp.web.cache.resource;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JOperationNotSupportedException;
import j.jave.kernal.jave.io.memory.JSingleDynamicMemoryCacheIO;
import j.jave.kernal.jave.io.memory.JSingleStaticMemoryCacheIO;
import j.jave.kernal.jave.support.resourceuri.DefaultIdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.IdentifierGenerator;
import j.jave.kernal.jave.support.resourceuri.InitialResource;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheRefreshEvent;
import j.jave.kernal.jave.support.resourceuri.ResourceCacheService;
import j.jave.platform.basicsupportcomp.support.ehcache.subhub.EhcacheService;

public abstract class ResourceCacheServiceSupport<T,M> implements ResourceCacheService,JSingleDynamicMemoryCacheIO<T> ,
JSingleStaticMemoryCacheIO<M>,InitialResource{

	private EhcacheService ehcacheService=JServiceHubDelegate.get().getService(this, EhcacheService.class);

	private static DefaultIdentifierGenerator defaultIdentifierGenerator=new DefaultIdentifierGenerator();
	@Override
	public IdentifierGenerator generator() {
		return defaultIdentifierGenerator;
	}

	@Override
	public T set(String key, T object) {
		return (T) ehcacheService.put(generator().key(key), object);
	}

	@Override
	public T get(String key) {
		return (T) ehcacheService.get(generator().key(key));
	}
	
	@Override
	public final M set() {
		initResource(JConfiguration.get());
		return null;
	}

	@Override
	public T remove(String key) {
		return (T) ehcacheService.remove(generator().key(key));
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
