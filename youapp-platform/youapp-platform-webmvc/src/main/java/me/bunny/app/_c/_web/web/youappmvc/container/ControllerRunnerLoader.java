package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.sps.multiv.ComponentVersionApplication;
import me.bunny.kernel.container.JMicroContainerConfig;
import me.bunny.kernel.container.JRunner;
import me.bunny.kernel.container.JRunnerLoader;

import org.springframework.context.ApplicationContext;

class ControllerRunnerLoader implements JRunnerLoader{

	private ApplicationContext applicationContext;
	
	private ControllerMicroContainerConfig controllerMicroContainerConfig;
	
	private ComponentVersionApplication componentVersionApplication;
	
	public ControllerRunnerLoader(ApplicationContext applicationContext,
			ControllerMicroContainerConfig controllerMicroContainerConfig,
			ComponentVersionApplication componentVersionApplication
			) {
		this.applicationContext=applicationContext;
		this.controllerMicroContainerConfig=controllerMicroContainerConfig;
		this.componentVersionApplication=componentVersionApplication;
	}
	
	public JRunner load(JMicroContainerConfig envContainerConfig) {
		ControllerRunner controllerRunner=new ControllerRunner(applicationContext,componentVersionApplication,controllerMicroContainerConfig);
		return controllerRunner;
	};
}
