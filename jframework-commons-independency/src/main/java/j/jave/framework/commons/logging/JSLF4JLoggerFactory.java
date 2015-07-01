package j.jave.framework.commons.logging;


class JSLF4JLoggerFactory implements JILoggerFactory {

	private static JSLF4JLoggerFactory jsl4jLoggerFactory;
	
	private volatile boolean available=false;
	
	private volatile boolean availableTested=false;
	
	private Object sync=new Object();
	
	private JSLF4JLoggerFactory() {
	}
	
	static JSLF4JLoggerFactory get(){
		if(jsl4jLoggerFactory==null){
			synchronized (JSLF4JLoggerFactory.class) {
				if(jsl4jLoggerFactory==null){
					jsl4jLoggerFactory=new JSLF4JLoggerFactory();
				}
			}
		}
		return jsl4jLoggerFactory;
	}
	
	@Override
	public JLogger getLogger(String name) {
		return new JSLF4JLogWrapper(name);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		return new JSLF4JLogWrapper(clazz);
	}
	
	
	
	@Override
	public boolean available() {
		if(!availableTested){
			synchronized (sync) {
				if(!availableTested){
					try{
						new JSLF4JLogWrapper(JSLF4JLoggerFactory.class);
						available=true;
					}catch(Throwable e){
						available=false;
					}
					availableTested=true;
				}
			}
		}
		return available;
	}


}
