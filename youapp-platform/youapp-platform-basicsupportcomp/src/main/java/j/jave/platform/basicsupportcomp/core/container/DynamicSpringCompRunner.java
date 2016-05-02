package j.jave.platform.basicsupportcomp.core.container;

import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationContext;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;

public class DynamicSpringCompRunner extends SpringCompRunner {
	
	public DynamicSpringCompRunner(SpringDynamicJARApplicationContext dynamicJARApplicationCotext,SpringCompMicroContainerConfig springCompMicroContainerConfig) {
		super(dynamicJARApplicationCotext,dynamicJARApplicationCotext.getComponentVersionApplication(),springCompMicroContainerConfig);
	}

	public DynamicComponentVersionApplication getComponentVersionApplication() {
		return (DynamicComponentVersionApplication) super.getComponentVersionApplication();
	}
	
	@Override
	public SpringDynamicJARApplicationContext getApplicationContext() {
		return (SpringDynamicJARApplicationContext) super.getApplicationContext();
	}
}