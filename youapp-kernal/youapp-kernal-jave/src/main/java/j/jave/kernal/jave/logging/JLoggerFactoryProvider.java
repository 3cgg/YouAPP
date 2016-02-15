package j.jave.kernal.jave.logging;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.extension.JExtensionProvider;
import j.jave.kernal.jave.reflect.JClassUtils;


public class JLoggerFactoryProvider implements JExtensionProvider {

	private final static JLogger LOGGER=JLoggerFactoryProvider.getLoggerFactory().getLogger(JLoggerFactoryProvider.class);
	
	public static JILoggerFactory getLoggerFactory(JConfiguration configuration){
		if(configuration==null){
			return JSystemConsoleLoggerFactory.get();
		}
		String loggerFactoryString=configuration.getString(JProperties.LOGGER_FACTORY, JSLF4JLoggerFactory.class.getName());
		if(loggerFactoryString.equals(JSLF4JLoggerFactory.class.getName())){
			return JSLF4JLoggerFactory.get();
		}
		else{
			try {
				Class<?> clazz=JClassUtils.load(loggerFactoryString, Thread.currentThread().getContextClassLoader());
				return (JILoggerFactory) clazz.newInstance();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				LOGGER.info("use system console logging.");
				return JSystemConsoleLoggerFactory.get();
//				throw new JInitializationException(e);
			} 
		}
		
	}
	
	public static JILoggerFactory getLoggerFactory(){
		return getLoggerFactory(JConfiguration.get());
	}
}