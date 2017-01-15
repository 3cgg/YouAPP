package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.sps.core.container.SpringContainerConfig;
import me.bunny.kernel.container.JContainerConfig;

public class InnerHttpInvokeContainerConfig extends JContainerConfig {

	private SpringContainerConfig springContainerConfig;
	
	public SpringContainerConfig getSpringContainerConfig() {
		return springContainerConfig;
	}
	
	public void setSpringContainerConfig(
			SpringContainerConfig springContainerConfig) {
		this.springContainerConfig = springContainerConfig;
	}
	
}
