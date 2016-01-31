package j.jave.kernal.http;

import j.jave.kernal.http.context.JHttpContext;
import j.jave.kernal.http.extension.JHttpBase;
import j.jave.kernal.http.extension.JIHttpFactory;
import j.jave.kernal.jave.exception.JInitializationException;

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
						JIHttpFactory httpFactory=JHttpContext.get().getHttpFactoryProvider().getHttpFactory();
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
