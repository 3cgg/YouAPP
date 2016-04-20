package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

import java.net.URI;

public class SpringCompMicroContainer implements MicroContainer{

	private SpringCompMicroContainerConfig springCompMicroContainerConfig;
	
	private JSpringCompRunner springCompRunner;
	
	private String name;
	
	private String unique;
	
	public SpringCompMicroContainer(SpringContainerConfig springContainerConfig,SpringCompMicroContainerConfig springCompMicroContainerConfig) {
		this.springCompMicroContainerConfig=springCompMicroContainerConfig;
		JSpringCompRunnerLoader springCompRunnerLoader=new JSpringCompRunnerLoader(
				springContainerConfig.getApplicationContext(), springContainerConfig.getJarUrls());
		springCompRunner=(JSpringCompRunner) springCompRunnerLoader.load(springCompMicroContainerConfig);
		this.name=springCompMicroContainerConfig.getName();
		this.unique=springCompMicroContainerConfig.getUnique();
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
	public String unique() {
		return this.unique;
	}

	@Override
	public String name() {
		return this.name;
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void restart() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public JRunner getRunner() {
		return springCompRunner;
	}

	@Override
	public void setRunner(JRunner runner) {
		this.springCompRunner=(JSpringCompRunner) runner;
	}

	@Override
	public MicroContainerConfig getContainerConfig() {
		return springCompMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.springCompMicroContainerConfig=(SpringCompMicroContainerConfig) containerConfig;
	}

	public SpringDynamicJARApplicationCotext getDynamicJARApplicationCotext() {
		return springCompRunner.getDynamicJARApplicationCotext();
	}
	
	public ComponentVersionApplication getComponentVersionApplication() {
		return springCompRunner.getComponentVersionApplication();
	}
	
}
