package j.jave.framework.commons.extension;

import j.jave.framework.commons.base64.JBase64FactoryProvider;
import j.jave.framework.commons.base64.JIBase64Factory;
import j.jave.framework.commons.context.JJaveContext;
import j.jave.framework.commons.logging.JILoggerFactory;
import j.jave.framework.commons.logging.JLoggerFactoryProvider;

/**
 * Manager all extension plugins, also include initialization API. Be aware we
 * recommend you initialize them using {@link JContext} instead of . The class
 * focuses the case of how and what to manager all plugins.
 */
public class JExtensionManager implements JLoggerFactoryProvider,
		JBase64FactoryProvider {

	private static JExtensionManager extensionManager = new JExtensionManager();

	private JExtensionManager() {
	}

	public static JExtensionManager get() {
		return extensionManager;
	}

	@Override
	public void setBase64Factory(Class<? extends JIBase64Factory> clazz) {
		JJaveContext.get().getBase64FactoryProvider()
				.setBase64Factory(clazz);
	}

	@Override
	public void setBase64Factory(JIBase64Factory factory) {
		JJaveContext.get().getBase64FactoryProvider()
				.setBase64Factory(factory);
	}

	@Override
	public JIBase64Factory getBase64Factory() {
		return JJaveContext.get().getBase64FactoryProvider()
				.getBase64Factory();
	}

	@Override
	public void setLoggerFactory(Class<? extends JILoggerFactory> clazz) {
		JJaveContext.get().getLoggerFactoryProvider()
				.setLoggerFactory(clazz);
	}

	@Override
	public void setLoggerFactory(JILoggerFactory loggerFactory) {
		JJaveContext.get().getLoggerFactoryProvider()
				.setLoggerFactory(loggerFactory);
	}

	@Override
	public JILoggerFactory getLoggerFactory() {
		return JJaveContext.get().getLoggerFactoryProvider()
				.getLoggerFactory();
	}

}
