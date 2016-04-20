package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;

public class ControllerMicroContainerConfig extends MicroContainerConfig {
	
	private SpringDynamicJARApplicationCotext dynamicJARApplicationCotext;

	public ControllerMicroContainerConfig(
			SpringDynamicJARApplicationCotext dynamicJARApplicationCotext) {
		this.dynamicJARApplicationCotext = dynamicJARApplicationCotext;
	}

	public SpringDynamicJARApplicationCotext getDynamicJARApplicationCotext() {
		return dynamicJARApplicationCotext;
	}
	
	
}
