package me.bunny.kernel._c.logging;

import me.bunny.kernel._c.extension.JIFactory;

public interface JILoggerFactory extends JIFactory {
	JLogger getLogger(String name);
	
	JLogger getLogger(Class<?> clazz);
	
}
