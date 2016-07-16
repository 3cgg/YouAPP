package j.jave.kernal.container;

import j.jave.kernal.container._resource.JResourceAccessService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.support._resource.JResourceStreamException;

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
		try {
			return resourceAccessService.execute(uri, object);
		} catch (Exception e) {
			throw new JResourceStreamException(e);
		}
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
