package j.jave.framework.extension.logger;

import j.jave.framework.extension.JExtensionProvider;


public interface JLoggerFactoryProvider extends JExtensionProvider {

	public abstract void setLoggerFactory(Class<? extends JILoggerFactory> clazz);

	public abstract void setLoggerFactory(JILoggerFactory loggerFactory);

	public abstract Object getLoggerFactory();

}