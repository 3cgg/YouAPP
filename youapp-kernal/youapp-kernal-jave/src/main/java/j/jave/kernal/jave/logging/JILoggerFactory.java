package j.jave.kernal.jave.logging;

import j.jave.kernal.jave.extension.JIFactory;

public interface JILoggerFactory extends JIFactory {
	JLogger getLogger(String name);
	
	JLogger getLogger(Class<?> clazz);
	
}
