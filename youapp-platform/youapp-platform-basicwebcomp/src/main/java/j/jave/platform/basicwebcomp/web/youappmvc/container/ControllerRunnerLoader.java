package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;

import org.springframework.context.ApplicationContext;

public class ControllerRunnerLoader implements JRunnerLoader{

	private ApplicationContext applicationContext;
	
	public ControllerRunnerLoader(ApplicationContext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	public JRunner load(MicroContainerConfig envContainerConfig) {
		ControllerRunner controllerRunner=new ControllerRunner((SpringDynamicJARApplicationCotext) applicationContext);
		return controllerRunner;
	};
}
