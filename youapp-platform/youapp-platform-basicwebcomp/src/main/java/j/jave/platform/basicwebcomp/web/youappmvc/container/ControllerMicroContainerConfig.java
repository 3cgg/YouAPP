package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.MicroContainerConfig;

import org.springframework.context.ApplicationContext;

class ControllerMicroContainerConfig extends MicroContainerConfig {
	
	private ApplicationContext applicationContext;

	public ControllerMicroContainerConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
}
