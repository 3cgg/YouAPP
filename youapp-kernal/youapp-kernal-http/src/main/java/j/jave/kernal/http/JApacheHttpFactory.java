package j.jave.kernal.http;

import j.jave.kernal.http.extension.JHttpBase;
import j.jave.kernal.http.extension.JIHttpFactory;

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
	public JHttpBase<?> getHttpPut() {
		return new JHttpPut();
	}
	
	@Override
	public JHttpBase<?> getHttpDelete() {
		return new JHttpDelete();
	}
	
	@Override
	public boolean available() {
		return true;
	}

}
