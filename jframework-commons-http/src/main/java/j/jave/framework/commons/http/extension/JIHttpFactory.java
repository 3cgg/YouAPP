package j.jave.framework.commons.http.extension;

import j.jave.framework.commons.extension.JIFactory;

public interface JIHttpFactory extends JIFactory {

	JHttpBase<?> getHttpPost();
	
	JHttpBase<?> getHttpGet();
	
	JHttpBase<?> getHttpPut();
	
	JHttpBase<?> getHttpDelete();
}
