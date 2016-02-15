package j.jave.kernal.jave.logging;


public class JLoggerFactory {
	
	private static JILoggerFactory loggerFactory;
	
	private static JILoggerFactory getLoggerFactory() {
		return JLoggerFactoryProvider.getLoggerFactory();
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
