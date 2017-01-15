package me.bunny.app._c._web.web.youappmvc.container;

import me.bunny.app._c.sps.core.container.SpringContainerConfig;
import me.bunny.app._c.sps.multiv.ComponentVersionTestApplication;
import me.bunny.app._c.sps.multiv.PlatformComponentVersionApplication;
import me.bunny.kernel.container.JContainerDelegate;
import me.bunny.kernel.eventdriven.servicehub.JServiceHubDelegate;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

@Service(value="PLATFORM_REQEUST_INVOKE_CONTAINER_INITIALIZE_BEAN")
public class PlatformRequestInvokeContainerInitializeBean implements ApplicationContextAware {

	private HttpInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	private ApplicationContext applicationContext;
	
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		this.applicationContext=applicationContext;
		SpringContainerConfig springContainerConfig=new SpringContainerConfig();
		springContainerConfig.setApplicationContext(this.applicationContext);
		InnerHttpInvokeContainerConfig config=new InnerHttpInvokeContainerConfig();
		config.setSpringContainerConfig(springContainerConfig);
		PlatformComponentVersionApplication platformComponentVersionApplication=new PlatformComponentVersionApplication();
		String unique=requestInvokeContainerDelegateService.newInstance(config, platformComponentVersionApplication);
		
		//startup test container.
		ComponentVersionTestApplication componentVersionTestApplication=new ComponentVersionTestApplication();
		InnerHttpInvokeTestContainerConfig testConfig=new InnerHttpInvokeTestContainerConfig();
		testConfig.setSpringContainerConfig(springContainerConfig);
		requestInvokeContainerDelegateService.newInstance(testConfig, 
				componentVersionTestApplication,(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
	
		//startup mock container.
		ComponentVersionTestApplication mockApplication
		=new ComponentVersionTestApplication(componentVersionTestApplication.getApp(),
				"COM-MOCK", 
				componentVersionTestApplication.getVersion(), 
				componentVersionTestApplication.getUrlPrefix());
		
		InnerHttpInvokeTestContainerConfig mockConfig=new InnerHttpInvokeTestContainerConfig();
		mockConfig.setSpringContainerConfig(springContainerConfig);
		mockConfig.setControllerObjectGetter(new DefaultControllerMockObjectGetter());
		
		requestInvokeContainerDelegateService.newInstance(mockConfig, mockApplication,
				(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
		
		
	}
	
}
