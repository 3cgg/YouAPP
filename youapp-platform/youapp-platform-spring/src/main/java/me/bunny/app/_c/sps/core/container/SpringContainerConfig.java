package me.bunny.app._c.sps.core.container;

import org.springframework.context.ApplicationContext;

import me.bunny.kernel.container.JContainerConfig;

public class SpringContainerConfig extends JContainerConfig{
	
	/**
	 * the root application/or parent application
	 */
	private ApplicationContext applicationContext;
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
}
