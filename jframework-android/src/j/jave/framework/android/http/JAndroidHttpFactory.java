package j.jave.framework.android.http;

import j.jave.framework.extension.http.JHttpBase;
import j.jave.framework.extension.http.JIHttpFactory;

public class JAndroidHttpFactory implements JIHttpFactory {

	private static JAndroidHttpFactory androidHttpFactory;
	
	private JAndroidHttpFactory() {
	}
	
	public static JAndroidHttpFactory get(){
		if(androidHttpFactory==null){
			synchronized (JAndroidHttpFactory.class) {
				if(androidHttpFactory==null){
					androidHttpFactory=new JAndroidHttpFactory();
				}
			}
		}
		return androidHttpFactory;
	}
	
	
	@Override
	public JHttpBase<?> getHttpPost() {
		return new JAndroidHttpPost();
	}

	@Override
	public JHttpBase<?> getHttpGet() {
		return new JAndroidHttpGet();
	}

}
