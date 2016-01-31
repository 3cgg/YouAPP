package j.jave.kernal.http.extension;

import j.jave.kernal.jave.extension.JExtensionProvider;


public interface JHttpFactoryProvider extends JExtensionProvider {

	public abstract void setHttpFactory(Class<? extends JIHttpFactory> clazz);

	public abstract void setHttpFactory(JIHttpFactory httpFactory);

	public abstract JIHttpFactory getHttpFactory();

}