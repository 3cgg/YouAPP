package me.bunny.kernel.security.service;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JDESedeCipherServiceFactory extends JServiceFactorySupport<JDESedeCipherService> {

	private final JLogger LOGGER=JLoggerFactory.getLogger(JDESedeCipherServiceFactory.class);
	
	private JDefaultDESedeCipherService defaultDESedeCipherService=null;
	
	public JDESedeCipherServiceFactory() {
		super(JDESedeCipherService.class);
	}
	private Object sync=new Object();
	
	@Override
	protected JDESedeCipherService doGetService() {
		if(defaultDESedeCipherService==null){
			synchronized (sync) {
				if(defaultDESedeCipherService==null){
					LOGGER.debug("Initializing DESede Cipher...");
					JDESedeCipherConfig cipherConfig=new JDESedeCipherConfig();
					String key=JConfiguration.get().getString(JProperties.SECURITY_DESEDE_KEY, "the.default.key.~!@#$%^&*()_+.");
					cipherConfig.setKey(key);
					defaultDESedeCipherService=new JDefaultDESedeCipherService(cipherConfig);
				}
			}
		}
		return defaultDESedeCipherService;
	}
	
	public static interface JDESedeCipherServiceSupport{
		public JDESedeCipherService getDESedeCipherService();
	}

}
