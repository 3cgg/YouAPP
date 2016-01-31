package j.jave.kernal.jave.logging;

import j.jave.kernal.jave.extension.JExtensionProvider;


public interface JLoggerFactoryProvider extends JExtensionProvider {

	public abstract void setLoggerFactory(Class<? extends JILoggerFactory> clazz);

	public abstract void setLoggerFactory(JILoggerFactory loggerFactory);

	public abstract JILoggerFactory getLoggerFactory();

}