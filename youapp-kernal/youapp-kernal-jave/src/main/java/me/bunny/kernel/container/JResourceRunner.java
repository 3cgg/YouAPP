package me.bunny.kernel.container;

import java.net.URI;

import me.bunny.kernel._c.support._resource.JResourceStreamException;
import me.bunny.kernel.container.JExecutableURIUtil.Type;
import me.bunny.kernel.container._resource.JResourceAccessService;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

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
			JURIInfo uriInfo = JExecutableURIUtil.getURIInfo(uri);
			if(JExecutableURIUtil.isPut(uriInfo)){
				return resourceAccessService.execute(uri, object,Type.PUT);
			}
			if(JExecutableURIUtil.isDelete(uriInfo)){
				return resourceAccessService.execute(uri, object,Type.DELETE);
			}
			if(JExecutableURIUtil.isExist(uriInfo)){
				return resourceAccessService.execute(uri, object,Type.EXIST);
			}
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
