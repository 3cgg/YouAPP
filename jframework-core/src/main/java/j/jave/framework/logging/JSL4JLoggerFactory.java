package j.jave.framework.logging;

import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.extension.logger.JLogger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class JSL4JLoggerFactory implements JILoggerFactory {

	private static JSL4JLoggerFactory jsl4jLoggerFactory;
	
	private JSL4JLoggerFactory() {
	}
	
	static JSL4JLoggerFactory get(){
		if(jsl4jLoggerFactory==null){
			synchronized (JSL4JLoggerFactory.class) {
				if(jsl4jLoggerFactory==null){
					jsl4jLoggerFactory=new JSL4JLoggerFactory();
				}
			}
		}
		return jsl4jLoggerFactory;
	}
	
	@Override
	public JLogger getLogger(String name) {
		Logger logger=LoggerFactory.getLogger(name);
		return new JSL4JLogWrapper(logger);
	}

	@Override
	public JLogger getLogger(Class<?> clazz) {
		Logger logger=LoggerFactory.getLogger(clazz);
		return new JSL4JLogWrapper(logger);
	}


}
