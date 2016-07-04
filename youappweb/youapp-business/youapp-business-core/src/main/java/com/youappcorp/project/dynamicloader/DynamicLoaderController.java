package com.youappcorp.project.dynamicloader;

import j.jave.kernal.container.JContainerDelegate;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.sps.core.context.SpringContextSupport;
import j.jave.platform.sps.multiv.ComponentVersionTestApplication;
import j.jave.platform.sps.multiv.DynamicComponentVersionApplication;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.container.DefaultControllerMockObjectGetter;
import j.jave.platform.webcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeContainer;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeContainerConfig;
import j.jave.platform.webcomp.web.youappmvc.container.InnerHttpInvokeTestContainerConfig;
import j.jave.platform.webcomp.web.youappmvc.controller.ControllerSupport;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value="/dynamicloader")
public class DynamicLoaderController extends ControllerSupport {
	
	private HttpInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	@ResponseBody
	@RequestMapping(value="/loadjar")
	public ResponseModel loadjar(String jarName){
		ApplicationContext applicationContext=SpringContextSupport.getApplicationContext();
		
		try {
			String jarPath="D:\\temp\\"+jarName; //"D:\\temp\\youapp-business-bill-2.0.1.jar"
			URL url= new File(jarPath).toURI().toURL();
			URL[] jarUrls=new URL[]{url};
			InnerHttpInvokeContainerConfig dynamicSpringContainerConfig=new InnerHttpInvokeContainerConfig();
			dynamicSpringContainerConfig.setJarUrls(jarUrls);
			dynamicSpringContainerConfig.setApplicationContext(applicationContext);
			
			DynamicComponentVersionApplication dynamicComponentVersionApplication
				=new DynamicComponentVersionApplication(applicationContext, jarUrls);
			String unique=requestInvokeContainerDelegateService.newInstance(dynamicSpringContainerConfig, dynamicComponentVersionApplication);
			
			//startup test container.
			
			ComponentVersionTestApplication componentVersionTestApplication
			=new ComponentVersionTestApplication(dynamicComponentVersionApplication.getApp(),
					dynamicComponentVersionApplication.getComponent()+"-TEST", 
					dynamicComponentVersionApplication.getVersion(), 
					dynamicComponentVersionApplication.getUrlPrefix());
			
			InnerHttpInvokeTestContainerConfig testConfig=new InnerHttpInvokeTestContainerConfig();
			testConfig.setApplicationContext(applicationContext);
			testConfig.setJarUrls(jarUrls);
			
			requestInvokeContainerDelegateService.newInstance(testConfig, componentVersionTestApplication,
					(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
			
			//startup mock container.
			ComponentVersionTestApplication mockApplication
			=new ComponentVersionTestApplication(dynamicComponentVersionApplication.getApp(),
					dynamicComponentVersionApplication.getComponent()+"-MOCK", 
					dynamicComponentVersionApplication.getVersion(), 
					dynamicComponentVersionApplication.getUrlPrefix());
			
			InnerHttpInvokeTestContainerConfig mockConfig=new InnerHttpInvokeTestContainerConfig();
			mockConfig.setApplicationContext(applicationContext);
			mockConfig.setJarUrls(jarUrls);
			mockConfig.setControllerObjectGetter(new DefaultControllerMockObjectGetter());
			requestInvokeContainerDelegateService.newInstance(mockConfig, mockApplication,
					(InnerHttpInvokeContainer) JContainerDelegate.get().getContainer(unique));
			
			return ResponseModel.newSuccess().setData(true);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
