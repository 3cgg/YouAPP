package j.jave.kernal.http.extension;

import j.jave.kernal.JConfiguration;
import j.jave.kernal.JProperties;
import j.jave.kernal.http.JApacheHttpClientFactory;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.extension.JExtensionProvider;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.reflect.JClassUtils;


public class JHttpFactoryProvider implements JExtensionProvider {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(JHttpFactoryProvider.class);
	
	public static JIHttpFactory getHttpFactory(JConfiguration configuration){
		String httpFactoryString=configuration.getString(JProperties.HTTP_FACTORY, JApacheHttpClientFactory.class.getName());
		if(httpFactoryString.equals(JApacheHttpClientFactory.class.getName())){
			return JApacheHttpClientFactory.get();
		}
		else{
			Class<?> clazz=JClassUtils.load(httpFactoryString, Thread.currentThread().getContextClassLoader());
			try {
				return (JIHttpFactory) clazz.newInstance();
			} catch (Exception e) {
				LOGGER.error(e.getMessage(), e);
				throw new JInitializationException(e);
			} 
		}
	}
	
	public static JIHttpFactory getHttpFactory(){
		return getHttpFactory(JConfiguration.get());
	}

}