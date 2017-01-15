package me.bunny.modular._p.streaming.logging;

import me.bunny.kernel._c.logging.JILoggerFactory;
import me.bunny.kernel._c.logging.JLogger;

public class SimpleLoggerFactory implements JILoggerFactory{
	
	@Override
	public boolean available() {
		return true;
	}

	@Override
	public JLogger getLogger(String name) {
		return new ComplexLogger(name);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		return new ComplexLogger(clazz);
	}
	
	
	
	
	
	

}
