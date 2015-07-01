package j.jave.framework.commons.logging;

import j.jave.framework.commons.extension.JIFactory;

public interface JILoggerFactory extends JIFactory {
	JLogger getLogger(String name);
	
	JLogger getLogger(Class<?> clazz);
	
}
