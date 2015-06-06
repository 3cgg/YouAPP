package j.jave.framework.http;

import j.jave.framework.context.JContext;
import j.jave.framework.exception.JInitializationException;
import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.extension.http.JIHttpFactory;
import j.jave.framework.reflect.JClassUtils;

public class JHttpFactory {
	
	private static JIHttpFactory httpFactory;
	
	private static JIHttpFactory getHttpFactory() {
		
		if(httpFactory==null){
			synchronized (JIHttpFactory.class) {
				if(httpFactory==null){
					try {
						Object obj=JContext.get().getHttpFactoryProvider().getHttpFactory();
						if(obj==null){
							httpFactory=JDefaultHttpFactory.get();
						}
						else{
							if(JClassUtils.isClass(obj)){
								Class<? extends JIHttpFactory> clazz=(Class<? extends JIHttpFactory>) obj;
								httpFactory=clazz.newInstance();
							}
							else{
								httpFactory=(JIHttpFactory) obj;
							}
						}
					} catch (InstantiationException e) {
						throw new JInitializationException(e);
					} catch (IllegalAccessException e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return httpFactory;
	}

	public static JHttpBase<?> getHttpPost(){
		return getHttpFactory().getHttpPost();
	}
	
	public static JHttpBase<?> getHttpGet(){
		return getHttpFactory().getHttpGet();
	}
	
	
}
