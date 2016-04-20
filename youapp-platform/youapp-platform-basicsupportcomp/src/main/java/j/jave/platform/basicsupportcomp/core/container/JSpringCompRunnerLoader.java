package j.jave.platform.basicsupportcomp.core.container;

import j.jave.kernal.container.JRunner;
import j.jave.kernal.container.JRunnerLoader;
import j.jave.kernal.container.MicroContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class JSpringCompRunnerLoader implements JRunnerLoader{

	private ApplicationContext applicationContext;
	
	private URL[]  jarUrls;
	
	public JSpringCompRunnerLoader(ApplicationContext applicationContext,URL[]  jarUrls ) {
		this.applicationContext=applicationContext;
		this.jarUrls=jarUrls;
	}
	
	public JRunner load(MicroContainerConfig envContainerConfig) {
		ComponentVersionApplication componentVersionApplication=  JComponentVersionSpringApplicationSupport.loadComponent(applicationContext, jarUrls);
		JSpringCompRunner springCompRunner=new JSpringCompRunner(componentVersionApplication.getApplicationContext());
		return springCompRunner;
	};
}
