package j.jave.framework.extension.http;

import j.jave.framework.extension.JExtensionProvider;


public interface JHttpFactoryProvider extends JExtensionProvider {

	public abstract void setHttpFactory(Class<? extends JIHttpFactory> clazz);

	public abstract void setHttpFactory(JIHttpFactory httpFactory);

	public abstract Object getHttpFactory();

}