package j.jave.platform.sps.core.container;

import j.jave.kernal.container.JContainerConfig;

import org.springframework.context.ApplicationContext;

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
