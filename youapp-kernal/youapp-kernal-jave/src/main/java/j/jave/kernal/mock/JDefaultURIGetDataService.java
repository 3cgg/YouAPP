package j.jave.kernal.mock;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.container.JResourceContainer;
import j.jave.kernal.container.JResourceContainerConfig;
import j.jave.kernal.eventdriven.servicehub.JServiceFactorySupport;

import java.net.URI;
import java.util.Map;

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
