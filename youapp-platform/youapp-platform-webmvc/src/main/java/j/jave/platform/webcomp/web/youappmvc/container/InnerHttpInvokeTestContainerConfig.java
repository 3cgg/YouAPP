package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.platform.sps.core.container.DynamicSpringContainerConfig;

public class InnerHttpInvokeTestContainerConfig extends DynamicSpringContainerConfig {

	private ControllerObjectGetter controllerObjectGetter=new DefaultControllerMockObjectGetter();

	public ControllerObjectGetter getControllerObjectGetter() {
		return controllerObjectGetter;
	}

	public void setControllerObjectGetter(
			ControllerObjectGetter controllerObjectGetter) {
		this.controllerObjectGetter = controllerObjectGetter;
	}
	
}
