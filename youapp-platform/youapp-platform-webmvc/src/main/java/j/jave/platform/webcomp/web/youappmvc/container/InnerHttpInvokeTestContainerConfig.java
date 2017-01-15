package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.sps.core.container.SpringContainerConfig;
import me.bunny.kernel.container.JContainerConfig;

public class InnerHttpInvokeTestContainerConfig extends JContainerConfig {

	private SpringContainerConfig springContainerConfig;
	
	private ControllerObjectGetter controllerObjectGetter=new DefaultControllerMockObjectGetter();

	public SpringContainerConfig getSpringContainerConfig() {
		return springContainerConfig;
	}
	
	public void setSpringContainerConfig(
			SpringContainerConfig springContainerConfig) {
		this.springContainerConfig = springContainerConfig;
	}

	public ControllerObjectGetter getControllerObjectGetter() {
		return controllerObjectGetter;
	}

	public void setControllerObjectGetter(
			ControllerObjectGetter controllerObjectGetter) {
		this.controllerObjectGetter = controllerObjectGetter;
	}
	
}
