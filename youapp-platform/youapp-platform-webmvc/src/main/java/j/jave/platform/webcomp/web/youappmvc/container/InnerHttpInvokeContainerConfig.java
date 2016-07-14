package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainerConfig;
import j.jave.platform.sps.core.container.SpringContainerConfig;

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
