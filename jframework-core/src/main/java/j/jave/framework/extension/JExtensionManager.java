package j.jave.framework.extension;

import j.jave.framework.context.JContext;
import j.jave.framework.extension.base64.JBase64FactoryProvider;
import j.jave.framework.extension.base64.JIBase64Factory;
import j.jave.framework.extension.http.JHttpFactoryProvider;
import j.jave.framework.extension.http.JIHttpFactory;
import j.jave.framework.extension.logger.JILoggerFactory;
import j.jave.framework.extension.logger.JLoggerFactoryProvider;

/**
 * Manager all extension plugins, also include initialization API.  
 * Be aware we recommend you initialize them using {@link JContext} instead of .
 * The class focuses the case of how and what to manager all plugins.
 */
public final class JExtensionManager implements JHttpFactoryProvider, JLoggerFactoryProvider, JBase64FactoryProvider {

	private static JExtensionManager extensionManager=new JExtensionManager();
	
	private JExtensionManager(){
	}
	
	public static JExtensionManager get(){
		return extensionManager;
	}

	@Override
	public void setBase64Factory(Class<? extends JIBase64Factory> clazz) {
		JContext.get().getBase64FactoryProvider().setBase64Factory(clazz);
	}

	@Override
	public void setBase64Factory(JIBase64Factory factory) {
		JContext.get().getBase64FactoryProvider().setBase64Factory(factory);
	}

	@Override
	public Object getBase64Factory() {
		return JContext.get().getBase64FactoryProvider().getBase64Factory();
	}

	@Override
	public void setLoggerFactory(Class<? extends JILoggerFactory> clazz) {
		JContext.get().getLoggerFactoryProvider().setLoggerFactory(clazz);
	}

	@Override
	public void setLoggerFactory(JILoggerFactory loggerFactory) {
		JContext.get().getLoggerFactoryProvider().setLoggerFactory(loggerFactory);
	}

	@Override
	public Object getLoggerFactory() {
		return JContext.get().getLoggerFactoryProvider().getLoggerFactory();
	}

	@Override
	public void setHttpFactory(Class<? extends JIHttpFactory> clazz) {
		JContext.get().getHttpFactoryProvider().setHttpFactory(clazz);
	}

	@Override
	public void setHttpFactory(JIHttpFactory httpFactory) {
		JContext.get().getHttpFactoryProvider().setHttpFactory(httpFactory);
	}

	@Override
	public Object getHttpFactory() {
		return JContext.get().getHttpFactoryProvider().getHttpFactory();
	}
	

}
