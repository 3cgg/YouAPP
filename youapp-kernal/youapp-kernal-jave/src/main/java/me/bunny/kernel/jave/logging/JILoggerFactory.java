package me.bunny.kernel.jave.logging;

import me.bunny.kernel.jave.extension.JIFactory;

public interface JILoggerFactory extends JIFactory {
	JLogger getLogger(String name);
	
	JLogger getLogger(Class<?> clazz);
	
}
