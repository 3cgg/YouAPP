package me.bunny.kernel.jave.logging;

public class JSimplePipelineLoggerFactory implements JILoggerFactory{
	
	@Override
	public boolean available() {
		return true;
	}

	@Override
	public JLogger getLogger(String name) {
		return new JSimplePipelineLogger(name);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		return new JSimplePipelineLogger(clazz);
	}
	
	
	
	
	
	

}
