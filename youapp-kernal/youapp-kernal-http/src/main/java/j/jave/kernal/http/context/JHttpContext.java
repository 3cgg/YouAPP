package j.jave.kernal.http.context;

import j.jave.kernal.http.extension.JHttpFactoryProvider;
import j.jave.kernal.http.extension.JIHttpFactory;

import java.util.HashMap;

/**
 * the context goes through all the application.
 * the instance also providers the uniform initialization entrance for all extension plugins.
 * @author J
 * @see  
 * <p>{@link #getHttpFactoryProvider()}
 */
@SuppressWarnings("serial")
public class JHttpContext extends HashMap<String, Object> {
	
	private static JHttpContext context;
	
	private JHttpContext() {
	}
	
	public static JHttpContext get(){
		if(context==null){
			synchronized (JHttpContext.class) {
				if(context==null){
					context=new JHttpContext();
				}
			}
		}
		return context;
	}
	
	public  class HttpFactoryProvider implements JHttpFactoryProvider{
		private static final String EXT_HTTP_FACTORY_IMPLEMENTAION="j.jave.framework.context.JCommonsHttpContext.HttpFactoryProvider";

		@Override
		public void setHttpFactory(Class<? extends JIHttpFactory> clazz){
			put(EXT_HTTP_FACTORY_IMPLEMENTAION, clazz);
		}
		
		@Override
		public void setHttpFactory(JIHttpFactory httpFactory){
			put(EXT_HTTP_FACTORY_IMPLEMENTAION, httpFactory);
		}

		@Override
		public JIHttpFactory getHttpFactory(){
			return (JIHttpFactory) get(EXT_HTTP_FACTORY_IMPLEMENTAION);
		}
	}
	
	
	
	private HttpFactoryProvider httpFactoryProvider=new HttpFactoryProvider();
	
	public HttpFactoryProvider getHttpFactoryProvider() {
		return httpFactoryProvider;
	}
	
}
