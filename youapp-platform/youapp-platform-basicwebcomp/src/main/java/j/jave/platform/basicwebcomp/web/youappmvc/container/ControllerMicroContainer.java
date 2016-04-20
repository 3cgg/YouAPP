package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.MicroContainer;
import j.jave.kernal.container.MicroContainerConfig;

import java.net.URI;

public class ControllerMicroContainer implements MicroContainer{

	private ControllerMicroContainerConfig controllerMicroContainerConfig;
	
	private ControllerRunner controllerRunner;
	
	private String name;
	
	private String unique;
	
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
		this.controllerMicroContainerConfig=(SpringCompMicroContainerConfig) containerConfig;
	}

}
