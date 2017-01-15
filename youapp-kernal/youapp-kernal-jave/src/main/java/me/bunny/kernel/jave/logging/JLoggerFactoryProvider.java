package me.bunny.kernel.jave.logging;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel.jave.extension.JExtensionProvider;
import me.bunny.kernel.jave.reflect.JClassUtils;


public class JLoggerFactoryProvider implements JExtensionProvider {

	private final static JLogger LOGGER=JLoggerFactoryProvider.console()
			.getLogger(JLoggerFactoryProvider.class);
	
	public static JILoggerFactory getLoggerFactory(JConfiguration configuration){
		if(configuration==null){
			return JSystemConsoleLoggerFactory.get();
		}
		String loggerFactoryString=configuration.getString(JProperties.LOGGER_FACTORY, 
				JSLF4JLoggerFactory.class.getName());
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
	
	public static JILoggerFactory slf4j(){
		return JSLF4JLoggerFactory.get();
	}
	
	public static JILoggerFactory console(){
		return JSystemConsoleLoggerFactory.get();
	}
	
	
	
	
	
	
	
	
	
	
	
	
}