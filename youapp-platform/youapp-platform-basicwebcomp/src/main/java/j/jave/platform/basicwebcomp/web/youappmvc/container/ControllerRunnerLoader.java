package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.basicsupportcomp.core.SpringDynamicJARApplicationCotext;
import j.jave.platform.basicsupportcomp.core.container.MappingMeta;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ControllerRunnerLoader implements JRunnerLoader{

	private SpringDynamicJARApplicationCotext applicationContext;
	
	public ControllerRunnerLoader(SpringDynamicJARApplicationCotext applicationContext) {
		this.applicationContext=applicationContext;
	}
	
	public JRunner load(MicroContainerConfig envContainerConfig) {
		ControllerRunner controllerRunner=new ControllerRunner(applicationContext);
		return controllerRunner;
	};
}
