package j.jave.framework.commons.logging;

import j.jave.framework.commons.extension.JExtensionProvider;


public interface JLoggerFactoryProvider extends JExtensionProvider {

	public abstract void setLoggerFactory(Class<? extends JILoggerFactory> clazz);

	public abstract void setLoggerFactory(JILoggerFactory loggerFactory);

	public abstract JILoggerFactory getLoggerFactory();

}