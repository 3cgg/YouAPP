package j.jave.kernal.ehcache.ext;

import j.jave.kernal.ehcache.JDefaultEhcacheService;
import j.jave.kernal.ehcache.JEhcacheProperties;
import me.bunny.kernel.jave.service.JService;
import me.bunny.kernel.jave.service.JServiceInterfaceShared;
import me.bunny.kernel.jave.service.JXMLConfigProvider;

@JServiceInterfaceShared
public final class JEhcacheInterfaceProvider extends JXMLConfigProvider {
	
	private static final JEhcacheInterfaceProvider INSTANCE=new JEhcacheInterfaceProvider();
	
	private JEhcacheInterfaceProvider(){}
	
	public static JEhcacheInterfaceProvider get(){
		return INSTANCE;
	}

	@Override
	protected String key() {
		return JEhcacheProperties.DEFAULT_EHCACHE_SERVICE_INTERFACE;
	}

	@Override
	protected Class<? extends JService> defaultInterface() {
		return JDefaultEhcacheService.class;
	}
	
	@Override
	protected String desc() {
		return "Ehcache-provider";
	}
	
	
}
