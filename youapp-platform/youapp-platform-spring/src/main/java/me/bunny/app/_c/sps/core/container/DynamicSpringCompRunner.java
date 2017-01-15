package me.bunny.app._c.sps.core.container;

import me.bunny.app._c.sps.core.SpringDynamicJARApplicationContext;
import me.bunny.app._c.sps.multiv.DynamicComponentVersionApplication;

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
