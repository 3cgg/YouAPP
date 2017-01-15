package j.jave.platform.webcomp.web.youappmvc.container;

import org.springframework.context.ApplicationContext;

import me.bunny.kernel.container.JMicroContainerConfig;

class ControllerMicroContainerConfig extends JMicroContainerConfig {
	
	private ApplicationContext applicationContext;

	public ControllerMicroContainerConfig(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
	
	public ApplicationContext getApplicationContext() {
		return applicationContext;
	}
	
	
}
