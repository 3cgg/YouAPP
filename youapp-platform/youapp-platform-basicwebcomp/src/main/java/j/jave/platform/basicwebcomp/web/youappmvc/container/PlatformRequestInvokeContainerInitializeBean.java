package j.jave.platform.basicwebcomp.web.youappmvc.container;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicsupportcomp.core.container.SpringContainerConfig;
import j.jave.platform.multiversioncompsupportcomp.PlatformComponentVersionApplication;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service(value="PLATFORM_REQEUST_INVOKE_CONTAINER_INITIALIZE_BEAN")
public class PlatformRequestInvokeContainerInitializeBean implements ApplicationContextAware {

	private RequestInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,RequestInvokeContainerDelegateService.class);
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
		SpringContainerConfig springContainerConfig=new SpringContainerConfig();
		springContainerConfig.setApplicationContext(applicationContext);
		PlatformComponentVersionApplication platformComponentVersionApplication=new PlatformComponentVersionApplication();
		requestInvokeContainerDelegateService.newInstance(springContainerConfig, platformComponentVersionApplication);
	}
	
}
