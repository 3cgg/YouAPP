package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.ContainerConfig;

import org.springframework.context.ApplicationContext;

public class SpringContainerConfig extends ContainerConfig{
	
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
