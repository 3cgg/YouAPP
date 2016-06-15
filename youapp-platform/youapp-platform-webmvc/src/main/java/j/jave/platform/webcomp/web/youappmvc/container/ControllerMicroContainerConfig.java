package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.container.JMicroContainerConfig;

import org.springframework.context.ApplicationContext;

class ControllerMicroContainerConfig extends JMicroContainerConfig {
	
	private ApplicationContext applicationContext;

	public ControllerMicroContainerConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
}
