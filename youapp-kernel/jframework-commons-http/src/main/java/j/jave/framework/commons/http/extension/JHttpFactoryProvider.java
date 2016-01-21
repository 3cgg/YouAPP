package j.jave.framework.commons.http.extension;

import j.jave.framework.commons.extension.JExtensionProvider;


public interface JHttpFactoryProvider extends JExtensionProvider {

	public abstract void setHttpFactory(Class<? extends JIHttpFactory> clazz);

	public abstract void setHttpFactory(JIHttpFactory httpFactory);

	public abstract JIHttpFactory getHttpFactory();

}