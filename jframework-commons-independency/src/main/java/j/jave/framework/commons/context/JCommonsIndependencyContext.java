package j.jave.framework.commons.context;

import j.jave.framework.commons.base64.JBase64FactoryProvider;
import j.jave.framework.commons.base64.JIBase64Factory;
import j.jave.framework.commons.logging.JILoggerFactory;
import j.jave.framework.commons.logging.JLoggerFactoryProvider;

import java.util.HashMap;

/**
 * the context goes through all the application.
 * the instance also providers the uniform initialization entrance for all extension plugins.
 * @author J
 * @see  
 * <p>{@link #getLoggerFactoryProvider()}
 */
@SuppressWarnings("serial")
public class JCommonsIndependencyContext extends HashMap<String, Object> {
	
	private static JCommonsIndependencyContext context;
	
	private JCommonsIndependencyContext() {
	}
	
	public static JCommonsIndependencyContext get(){
		if(context==null){
			synchronized (JCommonsIndependencyContext.class) {
				if(context==null){
					context=new JCommonsIndependencyContext();
				}
			}
		}
		return context;
	}
	
	public class LoggerFactoryProvider implements JLoggerFactoryProvider{
		private static final String EXT_LOGGER_FACTORY_IMPLEMENTAION="j.jave.framework.commons.context.JCommonsIndependencyContext.LoggerFactoryProvider";

		@Override
		public void setLoggerFactory(Class<? extends JILoggerFactory> clazz){
			put(EXT_LOGGER_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setLoggerFactory(JILoggerFactory loggerFactory){
			put(EXT_LOGGER_FACTORY_IMPLEMENTAION, loggerFactory);
		}

		@Override
		public JILoggerFactory getLoggerFactory(){
			return (JILoggerFactory) get(EXT_LOGGER_FACTORY_IMPLEMENTAION);
		}
	}
	private LoggerFactoryProvider loggerFactoryProvider=new LoggerFactoryProvider();
	
	public LoggerFactoryProvider getLoggerFactoryProvider() {
		return loggerFactoryProvider;
	}
	
	
	
	public class Base64FactoryProvider implements JBase64FactoryProvider{
		private static final String EXT_BASE64_FACTORY_IMPLEMENTAION="j.jave.framework.commons.context.JCommonsIndependencyContext.Base64FactoryProvider";

		@Override
		public void setBase64Factory(Class<? extends JIBase64Factory> clazz){
			put(EXT_BASE64_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setBase64Factory(JIBase64Factory factory){
			put(EXT_BASE64_FACTORY_IMPLEMENTAION, factory);
		}
		
		@Override
		public JIBase64Factory getBase64Factory(){
			return  (JIBase64Factory) get(EXT_BASE64_FACTORY_IMPLEMENTAION);
		}
	}
	
	private Base64FactoryProvider base64FactoryProvider=new Base64FactoryProvider();
	
	public Base64FactoryProvider getBase64FactoryProvider() {
		return base64FactoryProvider;
	}

	
}
