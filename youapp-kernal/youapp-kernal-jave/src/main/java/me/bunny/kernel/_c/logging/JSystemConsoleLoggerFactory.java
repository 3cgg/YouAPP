package me.bunny.kernel._c.logging;


public class JSystemConsoleLoggerFactory implements JILoggerFactory {

	private static JSystemConsoleLoggerFactory systemConsoleLoggerFactory=new JSystemConsoleLoggerFactory();;
	
	private volatile boolean available=false;
	
	private volatile boolean availableTested=false;
	
	private Object sync=new Object();
	
	private JSystemConsoleLoggerFactory() {
	}
	
	public static JSystemConsoleLoggerFactory get(){
		return systemConsoleLoggerFactory;
	}
	
	@Override
	public JLogger getLogger(String name) {
		return new JSystemConsoleLoggerWrapper(name);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		return new JSystemConsoleLoggerWrapper(clazz.getName());
	}
	
	@Override
	public boolean available() {
		if(!availableTested){
			synchronized (sync) {
				if(!availableTested){
					available=true;
					availableTested=true;
				}
			}
		}
		return available;
	}


}
