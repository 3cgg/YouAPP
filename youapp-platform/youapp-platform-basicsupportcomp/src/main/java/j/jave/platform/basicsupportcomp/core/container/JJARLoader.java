package j.jave.platform.basicsupportcomp.core.container;

import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport;
import j.jave.platform.multiversioncompsupportcomp.JComponentVersionSpringApplicationSupport.ComponentVersionApplication;

import java.net.URL;

import org.springframework.context.ApplicationContext;

public class JJARLoader implements JLoader{

	private ApplicationContext applicationContext;
	
	private URL[]  jarUrls;
	
	public JJARLoader(ApplicationContext applicationContext,URL[]  jarUrls ) {
		this.applicationContext=applicationContext;
		this.jarUrls=jarUrls;
	}
	
	public JRunner load(MicroContainerConfig envContainerConfig) {
		ComponentVersionApplication componentVersionApplication=  JComponentVersionSpringApplicationSupport.loadComponent(applicationContext, jarUrls);
		JSpringCompRunner springCompRunner=new JSpringCompRunner(componentVersionApplication.getApplicationContext());
		return springCompRunner;
	};
}
