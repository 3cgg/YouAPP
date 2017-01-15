package me.bunny.kernel.mock;

import java.net.URI;
import java.util.Map;

import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.container.JResourceContainer;
import me.bunny.kernel.container.JResourceContainerConfig;
import me.bunny.kernel.eventdriven.servicehub.JServiceFactorySupport;

public class JDefaultURIGetDataService 
extends JServiceFactorySupport<JDefaultURIGetDataService>
implements JURIGetDataService {

	JResourceContainer resourceContainer= null;
	
	public JResourceContainer getResourceContainer() {
		if(resourceContainer==null){
			resourceContainer= JContainerDelegate.get().getContainer(JResourceContainerConfig.DEFAULT_UNIQUE);
		}
		return resourceContainer;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object getData(String uri, Map<String, Object>... params) throws Exception {
		Object data=getResourceContainer().execute(new URI(uri), params);
		return data;
	}
	
	@Override
	protected JDefaultURIGetDataService doGetService() {
		return this;
	}
}
