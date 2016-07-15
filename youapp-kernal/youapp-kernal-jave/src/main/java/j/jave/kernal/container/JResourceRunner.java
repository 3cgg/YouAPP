package j.jave.kernal.container;

import j.jave.kernal.container._resource.JResourceAccessService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import java.net.URI;

public class JResourceRunner implements JRunner {

	private JResourceMicroContainerConfig resourceMicroContainerConfig; 
	
	private JResourceAccessService resourceAccessService=JServiceHubDelegate.get()
			.getService(this, JResourceAccessService.class); 
	
	public JResourceRunner(
			JResourceMicroContainerConfig resourceMicroContainerConfig) {
		super();
		this.resourceMicroContainerConfig = resourceMicroContainerConfig;
	}

	@Override
	public boolean accept(URI uri) {
		return true;
	}

	@Override
	public Object execute(URI uri, Object object) {
		return resourceAccessService.execute(uri, object);
	}

	@Override
	public String unique() {
		return resourceMicroContainerConfig.unique();
	}

	@Override
	public String name() {
		return resourceMicroContainerConfig.name();
	}

}
