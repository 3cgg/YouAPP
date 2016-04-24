package com.youappcorp.project.dynamicloader;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.platform.basicsupportcomp.core.container.DynamicSpringContainerConfig;
import j.jave.platform.basicsupportcomp.core.context.SpringContextSupport;
import j.jave.platform.basicwebcomp.web.model.ResponseModel;
import j.jave.platform.basicwebcomp.web.youappmvc.container.RequestInvokeContainerDelegateService;
import j.jave.platform.basicwebcomp.web.youappmvc.controller.ControllerSupport;
import j.jave.platform.multiversioncompsupportcomp.DynamicComponentVersionApplication;

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
	
	private RequestInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,RequestInvokeContainerDelegateService.class);
	
	@ResponseBody
	@RequestMapping(value="/loadjar")
	public ResponseModel loadjar(String jarName){
		ApplicationContext applicationContext=SpringContextSupport.getApplicationContext();
		
		try {
			String jarPath="D:\\temp\\"+jarName; //"D:\\temp\\youapp-business-bill-2.0.1.jar"
			URL url= new File(jarPath).toURI().toURL();
			URL[] jarUrls=new URL[]{url};
			DynamicSpringContainerConfig dynamicSpringContainerConfig=new DynamicSpringContainerConfig();
			dynamicSpringContainerConfig.setJarUrls(jarUrls);
			dynamicSpringContainerConfig.setApplicationContext(applicationContext);
			
			DynamicComponentVersionApplication dynamicComponentVersionApplication
				=new DynamicComponentVersionApplication(applicationContext, jarUrls);
			requestInvokeContainerDelegateService.newInstance(dynamicSpringContainerConfig, dynamicComponentVersionApplication);
			return ResponseModel.newSuccess().setData(true);
		} catch (MalformedURLException e) {
			throw new RuntimeException(e);
		}
	}
	
}
