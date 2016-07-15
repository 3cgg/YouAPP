package j.jave.kernal.container;

import j.jave.kernal.container._resource.JResourceURIParserService;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;

import java.net.URI;

public class JResourceContainer implements JExecutor, JContainer,JResourceURIParserGetter<JResourceURIParserService> {

	private JResourceContainerConfig resourceContainerConfig;
	
	private JResourceMicroContainer resourceMicroContainer;
	
	private JResourceURIParserService resourceURIParserService
	=JServiceHubDelegate.get().getService(this, JResourceURIParserService.class);
	
	public JResourceContainer(JResourceContainerConfig resourceContainerConfig) {
		this.resourceContainerConfig = resourceContainerConfig;
	}

	@Override
	public void initialize() {
		JResourceMicroContainerConfig resourceMicroContainerConfig
		=new JResourceMicroContainerConfig();
		resourceMicroContainerConfig.setName(name());
		resourceMicroContainerConfig.setUnique(unique());
		this.resourceMicroContainer=new JResourceMicroContainer(resourceMicroContainerConfig);
		
		resourceMicroContainer.initialize();
		
		JContainerDelegate.get().register(this);
	}

	@Override
	public void destroy() {
		resourceMicroContainer.destroy();
	}

	@Override
	public void restart() {
		resourceMicroContainer.restart();
	}

	@Override
	public String unique() {
		return resourceContainerConfig.unique();
	}

	@Override
	public String name() {
		return resourceContainerConfig.name();
	}

	@Override
	public boolean accept(URI uri) {
		return true;
	}

	@Override
	public Object execute(URI uri, Object object) {
		return resourceMicroContainer.execute(uri, object);
	}

	@Override
	public JResourceURIParserService resourceURIParser() {
		return resourceURIParserService;
	}

}
