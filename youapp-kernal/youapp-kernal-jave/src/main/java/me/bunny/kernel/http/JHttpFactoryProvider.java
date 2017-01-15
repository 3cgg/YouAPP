package me.bunny.kernel.http;

import me.bunny.kernel.JConfiguration;
import me.bunny.kernel.JProperties;
import me.bunny.kernel._c.exception.JInitializationException;
import me.bunny.kernel._c.extension.JExtensionProvider;
import me.bunny.kernel._c.logging.JLogger;
import me.bunny.kernel._c.logging.JLoggerFactory;
import me.bunny.kernel._c.reflect.JClassUtils;


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