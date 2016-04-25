package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.ComponentVersionApplication;

import java.net.URI;

class ControllerMicroContainer implements MicroContainer{

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
	public MicroContainerConfig getContainerConfig() {
		return controllerMicroContainerConfig;
	}

	@Override
	public void setContainerConfig(MicroContainerConfig containerConfig) {
		this.controllerMicroContainerConfig= (ControllerMicroContainerConfig) containerConfig;
	}
	
	static final String getGetRequest(String unique,String path){
		return ControllerRunner.getGetRequest(unique, path);
	}

	static final String getPutRequest(String unique,String path){
		return ControllerRunner.getPutRequest(unique, path);
	}
	
	static final String getExistRequest(String unique,String path){
		return ControllerRunner.getExistRequest(unique, path);
	}
	
	
}