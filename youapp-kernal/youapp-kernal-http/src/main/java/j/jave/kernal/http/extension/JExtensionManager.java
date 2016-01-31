package j.jave.kernal.http.extension;

import j.jave.kernal.http.context.JHttpContext;

/**
 * Manager all extension plugins, also include initialization API.  
 * Be aware we recommend you initialize them using {@link JContext} instead of .
 * The class focuses the case of how and what to manager all plugins.
 */
public class JExtensionManager implements JHttpFactoryProvider {

	private static JExtensionManager extensionManager=new JExtensionManager();
	
	private JExtensionManager(){
	}
	
	public static JExtensionManager get(){
		return extensionManager;
	}
	
	@Override
	public void setHttpFactory(Class<? extends JIHttpFactory> clazz) {
		JHttpContext.get().getHttpFactoryProvider().setHttpFactory(clazz);
	}

	@Override
	public void setHttpFactory(JIHttpFactory httpFactory) {
		JHttpContext.get().getHttpFactoryProvider().setHttpFactory(httpFactory);
	}

	@Override
	public JIHttpFactory getHttpFactory() {
		return JHttpContext.get().getHttpFactoryProvider().getHttpFactory();
	}
	

}
