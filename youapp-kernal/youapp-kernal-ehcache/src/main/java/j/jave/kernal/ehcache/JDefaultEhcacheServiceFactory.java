package j.jave.kernal.ehcache;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

public class JDefaultEhcacheServiceFactory extends JServiceFactorySupport<JDefaultEhcacheService> {

	private JDefaultEhcacheService defaultEhcacheService=new JDefaultEhcacheService(JConfiguration.get());
	
	@Override
	public JDefaultEhcacheService doGetService() {
		return defaultEhcacheService;
	}
	
	@Override
	public Class<?> getServiceImplClass() {
		return JDefaultEhcacheService.class;
	}
	
	@Override
	protected boolean isCanRegister() {
		boolean enable=JConfiguration.get().getBoolean(JEhcacheProperties.DEFAULT_EHCACHE_ENABLE, false);
		return enable;
	}
	
}
