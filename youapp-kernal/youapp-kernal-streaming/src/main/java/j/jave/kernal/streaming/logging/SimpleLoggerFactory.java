package j.jave.kernal.streaming.logging;

import me.bunny.kernel.jave.logging.JILoggerFactory;
import me.bunny.kernel.jave.logging.JLogger;

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
