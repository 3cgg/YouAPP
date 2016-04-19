package j.jave.platform.basicsupportcomp.core.container;

import java.net.URI;

public abstract class AbstractBaseContainer implements JExecutor,JIdentifier,JContainer {

	protected String unique;
	
	protected String name;
	
	protected MicroContainer springContext;
	
	protected final ContainerConfig containerConfig;
	
	public AbstractBaseContainer(ContainerConfig containerConfig) {
		this.containerConfig=containerConfig;
		this.name=containerConfig.getName();
		this.unique=containerConfig.getUnique();
		init(containerConfig);
	}
	
	private void init(ContainerConfig containerConfig){
		JJARLoader jarLoader=new JJARLoader(containerConfig.getApplicationContext(), containerConfig.getJarUrls());
		MicroContainerConfig microContainerConfig=new MicroContainerConfig();
		microContainerConfig.setLoader(jarLoader);
		springContext=MicroContainers.get().build(microContainerConfig);
	}
	
	@Override
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
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

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
