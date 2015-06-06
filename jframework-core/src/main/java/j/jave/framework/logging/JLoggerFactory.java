package j.jave.framework.logging;

import j.jave.framework.context.JContext;
import j.jave.framework.exception.JInitializationException;
import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.extension.logger.JLogger;
import j.jave.framework.reflect.JClassUtils;

public class JLoggerFactory {
	
	private static JILoggerFactory loggerFactory;
	
	private static JILoggerFactory getLoggerFactory() {
		
		if(loggerFactory==null){
			synchronized (JLoggerFactory.class) {
				if(loggerFactory==null){
					try {
						
						Object obj=JContext.get().getLoggerFactoryProvider().getLoggerFactory();
						if(obj==null){
							loggerFactory=JSL4JLoggerFactory.get();
						}
						else{
							if(JClassUtils.isClass(obj)){
								Class<? extends JILoggerFactory> clazz=(Class<? extends JILoggerFactory>) obj;
								loggerFactory=clazz.newInstance();
							}
							else{
								loggerFactory=(JILoggerFactory) obj;
							}
							
						}
					} catch (InstantiationException e) {
						throw new JInitializationException(e);
					} catch (IllegalAccessException e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return loggerFactory;
	}
	
	public static JLogger getLogger(String name){
		return getLoggerFactory().getLogger(name);
	}
	
	public static JLogger getLogger(Class<?> clazz){
		return getLoggerFactory().getLogger(clazz);
	}
	
	
}
