package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.sps.core.container.SpringContainerConfig;
import me.bunny.app._c.sps.multiv.ComponentVersionApplication;
import me.bunny.kernel.container.JExecutableURIGenerator;
import me.bunny.kernel.container.JMicroContainer;
import me.bunny.kernel.container.JMicroContainerConfig;
import me.bunny.kernel.container.JRunner;

import java.net.URI;

class ControllerMicroContainer implements JMicroContainer,JExecutableURIGenerator{

	private SpringContainerConfig springContainerConfig;
	
	private ControllerMicroContainerConfig controllerMicroContainerConfig;
	
	private ComponentVersionApplication componentVersionApplication;
	
	private ControllerRunner controllerRunner;
	
	private String name;
	
	private String unique;
	
	public ControllerMicroContainer(SpringContainerConfig springContainerConfig,
			ControllerMicroContainerConfig controllerMicroContainerConfig,
			ComponentVersionApplication componentVersionApplication
			) {
		this.springContainerConfig=springContainerConfig;
		this.controllerMicroContainerConfig=controllerMicroContainerConfig;
		this.componentVersionApplication=componentVersionApplication;
		this.name=controllerMicroContainerConfig.getName();
		this.unique=controllerMicroContainerConfig.getUnique();
	}
	
	@Override
	public boolean accept(URI uri) {
		return controllerRunner.accept(uri);
	}

	@Override
	public Object execute(URI uri, Object object) {
		return controllerRunner.execute(uri, object);
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
		ControllerRunnerLoader controllerRunnerLoader=new ControllerRunnerLoader(
				controllerMicroContainerConfig.getApplicationContext(),controllerMicroContainerConfig
				,componentVersionApplication
				);
		this.controllerRunner=(ControllerRunner) controllerRunnerLoader.load(controllerMicroContainerConfig);
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
		return controllerRunner;
	}

	@Override
	public void setRunner(JRunner runner) {
		this.controllerRunner= (ControllerRunner) runner;
	}

	@Override
	public JMicroContainerConfig getContainerConfig() {
		return controllerMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(JMicroContainerConfig containerConfig) {
		this.controllerMicroContainerConfig= (ControllerMicroContainerConfig) containerConfig;
	}

	@Override
	public String getGetRequestURI(String unique, String path) {
		return controllerRunner.getGetRequestURI(unique, path);
	}

	@Override
	public String getPutRequestURI(String unique, String path) {
		return controllerRunner.getPutRequestURI(unique, path);
	}

	@Override
	public String getDeleteRequestURI(String unique, String path) {
		return controllerRunner.getDeleteRequestURI(unique, path);
	}

	@Override
	public String getExistRequestURI(String unique, String path) {
		return controllerRunner.getExistRequestURI(unique, path);
	}
	
	
}
