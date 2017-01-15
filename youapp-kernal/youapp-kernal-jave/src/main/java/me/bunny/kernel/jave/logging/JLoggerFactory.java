package me.bunny.kernel.jave.logging;


public class JLoggerFactory {
	
	private static JILoggerFactory loggerFactory;
	
	private static final Object sync=new Object();
	
	private static JILoggerFactory getLoggerFactory() {
		if(loggerFactory==null){
			synchronized (sync) {
				if(loggerFactory==null){
					loggerFactory=JLoggerFactoryProvider.getLoggerFactory();
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
