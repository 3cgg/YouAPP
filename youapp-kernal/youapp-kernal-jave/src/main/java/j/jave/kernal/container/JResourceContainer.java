package j.jave.kernal.container;

import java.net.URI;

public class JResourceContainer implements JExecutor, JContainer {

	private JResourceContainerConfig resourceContainerConfig;
	
	private JResourceMicroContainer resourceMicroContainer;
	
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
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object execute(URI uri, Object object) {
		// TODO Auto-generated method stub
		return null;
	}

}
