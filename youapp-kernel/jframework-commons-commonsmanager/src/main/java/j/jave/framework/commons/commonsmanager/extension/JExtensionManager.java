package j.jave.framework.commons.commonsmanager.extension;

import j.jave.framework.commons.base64.JBase64FactoryProvider;
import j.jave.framework.commons.base64.JIBase64Factory;
import j.jave.framework.commons.context.JCommonsIndependencyContext;
import j.jave.framework.commons.http.context.JCommonsHttpContext;
import j.jave.framework.commons.http.extension.JHttpFactoryProvider;
import j.jave.framework.commons.http.extension.JIHttpFactory;
import j.jave.framework.commons.logging.JILoggerFactory;
import j.jave.framework.commons.logging.JLoggerFactoryProvider;

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
		JCommonsIndependencyContext.get().getBase64FactoryProvider().setBase64Factory(clazz);
	}

	@Override
	public void setBase64Factory(JIBase64Factory factory) {
		JCommonsIndependencyContext.get().getBase64FactoryProvider().setBase64Factory(factory);
	}

	@Override
	public JIBase64Factory getBase64Factory() {
		return JCommonsIndependencyContext.get().getBase64FactoryProvider().getBase64Factory();
	}

	@Override
	public void setLoggerFactory(Class<? extends JILoggerFactory> clazz) {
		JCommonsIndependencyContext.get().getLoggerFactoryProvider().setLoggerFactory(clazz);
	}

	@Override
	public void setLoggerFactory(JILoggerFactory loggerFactory) {
		JCommonsIndependencyContext.get().getLoggerFactoryProvider().setLoggerFactory(loggerFactory);
	}

	@Override
	public JILoggerFactory getLoggerFactory() {
		return JCommonsIndependencyContext.get().getLoggerFactoryProvider().getLoggerFactory();
	}

	@Override
	public void setHttpFactory(Class<? extends JIHttpFactory> clazz) {
		JCommonsHttpContext.get().getHttpFactoryProvider().setHttpFactory(clazz);
	}

	@Override
	public void setHttpFactory(JIHttpFactory httpFactory) {
		JCommonsHttpContext.get().getHttpFactoryProvider().setHttpFactory(httpFactory);
	}

	@Override
	public JIHttpFactory getHttpFactory() {
		return JCommonsHttpContext.get().getHttpFactoryProvider().getHttpFactory();
	}
	

}
