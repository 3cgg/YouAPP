package j.jave.framework.http;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.extension.http.JIHttpFactory;

class JDefaultHttpFactory implements JIHttpFactory {

	private static JDefaultHttpFactory defaultHttpFactory;
	
	private JDefaultHttpFactory() {
	}
	
	static JDefaultHttpFactory get(){
		if(defaultHttpFactory==null){
			synchronized (JDefaultHttpFactory.class) {
				if(defaultHttpFactory==null){
					defaultHttpFactory=new JDefaultHttpFactory();
				}
			}
		}
		return defaultHttpFactory;
	}
	
	@Override
	public JHttpBase<?> getHttpPost() {
		return new JHttpPost();
	}

	@Override
	public JHttpBase<?> getHttpGet() {
		return new JHttpGet();
	}

}
