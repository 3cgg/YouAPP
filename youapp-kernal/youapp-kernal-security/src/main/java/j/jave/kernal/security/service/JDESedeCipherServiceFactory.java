package j.jave.kernal.security.service;

import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.utils.JPropertiesUtils;

public class JDESedeCipherServiceFactory extends JServiceFactorySupport<JDESedeCipherService> {

	private final JLogger logger=JLoggerFactory.getLogger(JDESedeCipherServiceFactory.class);
	
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
					JDESedeCipherConfig cipherConfig=new JDESedeCipherConfig();
					try{
						String key=JPropertiesUtils.getKey("j.jave.framework.commons.security.desede.key", "commons-security.properties");
						cipherConfig.setKey(key);
					}catch(Exception e){
						logger.info("cannot load key from config file: commons-security.properties , use default value instead.");
					}
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
