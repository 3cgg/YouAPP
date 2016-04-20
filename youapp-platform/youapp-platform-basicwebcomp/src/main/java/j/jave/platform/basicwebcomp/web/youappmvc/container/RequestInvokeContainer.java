package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainer;
import j.jave.kernal.container.JExecutor;
import j.jave.kernal.container.JIdentifier;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.kernal.container.MicroContainers;
import j.jave.platform.basicsupportcomp.core.container.JSpringCompRunnerLoader;
import j.jave.platform.basicsupportcomp.core.container.SpringCompMicroContainer;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;

import java.net.URI;

public class RequestInvokeContainer implements JExecutor,JIdentifier,JContainer {

	private String unique;
	
	private String name;
	
	private SpringCompMicroContainer springCompMicroContainer;
	
	private MicroContainer springCompMicroContainer;
	
	protected final SpringContainerConfig springContainerConfig;
	
	public RequestInvokeContainer(SpringContainerConfig springContainerConfig) {
		this.springContainerConfig=springContainerConfig;
		this.name=springContainerConfig.getName();
		this.unique=springContainerConfig.getUnique();
		init(springContainerConfig);
	}
	
	private void init(SpringContainerConfig springContainerConfig){
		JSpringCompRunnerLoader jarLoader=new JSpringCompRunnerLoader(springContainerConfig.getApplicationContext(), springContainerConfig.getJarUrls());
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
