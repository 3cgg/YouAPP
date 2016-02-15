package j.jave.kernal.jave.base64;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.extension.JExtensionProvider;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;


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