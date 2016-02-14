package j.jave.kernal.security.service;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;

public class JDESedeCipherServiceFactory extends JServiceFactorySupport<JDESedeCipherService> {

	private final JLogger LOGGER=JLoggerFactory.getLogger(JDESedeCipherServiceFactory.class);
	
	private JDefaultDESedeCipherService defaultDESedeCipherService=null;
	
	public JDESedeCipherServiceFactory() {
		super(JDESedeCipherService.class);
	}
	private Object sync=new Object();
	
	@Override
	public JDESedeCipherService getService() {
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
