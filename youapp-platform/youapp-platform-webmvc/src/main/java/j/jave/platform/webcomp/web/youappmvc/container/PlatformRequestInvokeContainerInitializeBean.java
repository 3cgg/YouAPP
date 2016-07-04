package j.jave.platform.webcomp.web.youappmvc.container;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.sps.multiv.ComponentVersionTestApplication;
import j.jave.platform.sps.multiv.PlatformComponentVersionApplication;

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
		
		InnerHttpInvokeContainerConfig config=new InnerHttpInvokeContainerConfig();
		config.setApplicationContext(applicationContext);
		PlatformComponentVersionApplication platformComponentVersionApplication=new PlatformComponentVersionApplication();
		String unique=requestInvokeContainerDelegateService.newInstance(config, platformComponentVersionApplication);
		
		//startup test container.
		ComponentVersionTestApplication componentVersionTestApplication=new ComponentVersionTestApplication();
		InnerHttpInvokeTestContainerConfig testConfig=new InnerHttpInvokeTestContainerConfig();
		testConfig.setApplicationContext(applicationContext);
		requestInvokeContainerDelegateService.newInstance(testConfig, 
				componentVersionTestApplication,(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
	
		//startup mock container.
		ComponentVersionTestApplication mockApplication
		=new ComponentVersionTestApplication(componentVersionTestApplication.getApp(),
				"COM-MOCK", 
				componentVersionTestApplication.getVersion(), 
				componentVersionTestApplication.getUrlPrefix());
		
		InnerHttpInvokeTestContainerConfig mockConfig=new InnerHttpInvokeTestContainerConfig();
		mockConfig.setApplicationContext(applicationContext);
		mockConfig.setControllerObjectGetter(new DefaultControllerMockObjectGetter());
		
		requestInvokeContainerDelegateService.newInstance(mockConfig, mockApplication,
				(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
		
		
	}
	
}
