package j.jave.framework.commons.logging;

import j.jave.framework.commons.context.JCommonsIndependencyContext;
import j.jave.framework.commons.exception.JInitializationException;

public class JLoggerFactory {
	
	private static JILoggerFactory loggerFactory;
	
	private static JILoggerFactory getLoggerFactory() {
		
		if(loggerFactory==null){
			synchronized (JLoggerFactory.class) {
				if(loggerFactory==null){
					try {
						
						boolean getLogger=false;
						
						// get custom logger
						JILoggerFactory loggerFactory=JCommonsIndependencyContext.get().getLoggerFactoryProvider().getLoggerFactory();
						if(testLogger(loggerFactory)){
							getLogger=true;
						}
						
						//get sl4j 
						if(!getLogger){
							loggerFactory=JSLF4JLoggerFactory.get();
							if(testLogger(loggerFactory)){
								getLogger=true;
							}
						}
						
						//get system console
						if(!getLogger){
							loggerFactory=JSystemConsoleLoggerFactory.get();
							if(testLogger(loggerFactory)){
								getLogger=true;
							}
						}
						
						if(getLogger){
							JLoggerFactory.loggerFactory=loggerFactory;
						}
						else{
							System.out.println("Logger not found.");
						}
					} catch (Exception e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return loggerFactory;
	}

	private static boolean testLogger(JILoggerFactory loggerFactory) {
		return loggerFactory!=null&&loggerFactory.available();
	}
	
	public static JLogger getLogger(String name){
		return getLoggerFactory().getLogger(name);
	}
	
	public static JLogger getLogger(Class<?> clazz){
		return getLoggerFactory().getLogger(clazz);
	}
	
	
}
