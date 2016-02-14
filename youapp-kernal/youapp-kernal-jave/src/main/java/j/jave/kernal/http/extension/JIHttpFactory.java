package j.jave.kernal.http.extension;

import j.jave.kernal.http.JHttpBase;
import j.jave.kernal.jave.extension.JIFactory;

public interface JIHttpFactory extends JIFactory {

	JHttpBase<?> getHttpPost();
	
	JHttpBase<?> getHttpGet();
	
	JHttpBase<?> getHttpPut();
	
	JHttpBase<?> getHttpDelete();
}
