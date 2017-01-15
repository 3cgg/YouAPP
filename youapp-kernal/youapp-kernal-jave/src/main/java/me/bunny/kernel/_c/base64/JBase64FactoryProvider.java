package me.bunny.kernel._c.base64;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.extension.JExtensionProvider;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.reflect.JClassUtils;


public class JBase64FactoryProvider implements JExtensionProvider {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(JBase64FactoryProvider.class);
	
	public static JIBase64Factory getBase64Factory(JConfiguration configuration){
		
		String base64FactoryString=configuration.getString(JProperties.BASE64_FACTORY, JApachBase64Factory.class.getName());
		if(base64FactoryString.equals(JApachBase64Factory.class.getName())){
			return JApachBase64Factory.get();
		}
		else{
			Class<?> clazz=JClassUtils.load(base64FactoryString, Thread.currentThread().getContextClassLoader());
			try {
				return (JIBase64Factory) clazz.newInstance();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new JInitializationException(e);
			} 
		}
	}
	
	public static JIBase64Factory getBase64Factory(){
		return getBase64Factory(JConfiguration.get());
	}
	
	
}