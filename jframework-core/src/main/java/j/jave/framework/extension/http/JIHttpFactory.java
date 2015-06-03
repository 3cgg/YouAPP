package j.jave.framework.extension.http;

public interface JIHttpFactory {

	JHttpBase<?> getHttpPost();
	
	JHttpBase<?> getHttpGet();
	
}
