package j.jave.kernal.memcached.ext;

import j.jave.kernal.memcached.JDefaultMemcachedDisService;
import j.jave.kernal.memcached.JMemcacheProperties;
import me.bunny.kernel._c.service.JService;
import me.bunny.kernel._c.service.JServiceInterfaceShared;
import me.bunny.kernel._c.service.JXMLConfigProvider;

@JServiceInterfaceShared
public final class JMemcacheInterfaceProvider extends JXMLConfigProvider {
	
	private static final JMemcacheInterfaceProvider INSTANCE=new JMemcacheInterfaceProvider();
	
	private JMemcacheInterfaceProvider(){}
	
	public static JMemcacheInterfaceProvider get(){
		return INSTANCE;
	}

	@Override
	protected String key() {
		return JMemcacheProperties.DEFAULT_MEMCACHE_SERVICE_INTERFACE;
	}

	@Override
	protected Class<? extends JService> defaultInterface() {
		return JDefaultMemcachedDisService.class;
	}
	
	@Override
	protected String desc() {
		return "Memcache-provider";
	}
	
	
}
