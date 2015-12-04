package j.jave.framework.commons.http;

import j.jave.framework.commons.exception.JInitializationException;
import j.jave.framework.commons.http.context.JCommonsHttpContext;
import j.jave.framework.commons.http.extension.JHttpBase;
import j.jave.framework.commons.http.extension.JIHttpFactory;

public class JHttpFactory {
	
	private static JIHttpFactory httpFactory;
	
	private JHttpFactory(){}
	
	public static JIHttpFactory getHttpFactory() {
		
		if(httpFactory==null){
			synchronized (JIHttpFactory.class) {
				if(httpFactory==null){
					try {
						boolean getHttp=false;
						// test custom http factory
						JIHttpFactory httpFactory=JCommonsHttpContext.get().getHttpFactoryProvider().getHttpFactory();
						if(testHttp(httpFactory)){
							getHttp=true;
						}
						
						//test default http factory 
						if(!getHttp){
							httpFactory=JApacheHttpFactory.get();
							if(testHttp(httpFactory)){
								getHttp=true;
							}
						}
						
						if(getHttp){
							JHttpFactory.httpFactory=httpFactory;
						}
						else{
							System.out.println("Http Factory not found.");
						}
						
					} catch (Exception e) {
						throw new JInitializationException(e);
					}
				}
			}
		}
		return JHttpFactory.httpFactory;
	}

	private static boolean testHttp(JIHttpFactory httpFactory) {
		return httpFactory!=null&&httpFactory.available();
	}

	public static JHttpBase<?> getHttpPost(){
		return getHttpFactory().getHttpPost();
	}
	
	public static JHttpBase<?> getHttpGet(){
		return getHttpFactory().getHttpGet();
	}
	
	public static JHttpBase<?> getHttpPut(){
		return getHttpFactory().getHttpPut();
	}
	
	public static JHttpBase<?> getHttpDelete(){
		return getHttpFactory().getHttpDelete();
	}
}
