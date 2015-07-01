package j.jave.framework.commons.http;

import j.jave.framework.commons.http.extension.JHttpBase;
import j.jave.framework.commons.http.extension.JIHttpFactory;

class JApacheHttpFactory implements JIHttpFactory {

	private static JApacheHttpFactory defaultHttpFactory;
	
	private JApacheHttpFactory() {
	}
	
	static JApacheHttpFactory get(){
		if(defaultHttpFactory==null){
			synchronized (JApacheHttpFactory.class) {
				if(defaultHttpFactory==null){
					defaultHttpFactory=new JApacheHttpFactory();
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
	
	@Override
	public boolean available() {
		return true;
	}

}
