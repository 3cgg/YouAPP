package com.youappcorp.project.containermanager.service;

import j.jave.kernal.container.rhttp.JRemoteHttpContainerConfig;
import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedEvent;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedListener;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.web.youappmvc.container.HttpInvokeContainerDelegateService;
import j.jave.platform.multiversioncompsupportcomp.RemoteHttpComponentVersionApplication;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.model.AppMeta;

@Service
public class ContainerInitializingService
extends SpringServiceFactorySupport<ContainerInitializingService>
implements SpringApplicationContextInitializedListener ,JService {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(ContainerInitializingService.class);
	
	private HttpInvokeContainerDelegateService requestInvokeContainerDelegateService=
			JServiceHubDelegate.get().getService(this,HttpInvokeContainerDelegateService.class);
	
	
	@Autowired
	private ContainerManagerService containerManagerService;
	
	@Override
	public Object trigger(SpringApplicationContextInitializedEvent event) {
		
		List<AppMeta> appMetas= containerManagerService.getAllAppMetas(DefaultServiceContext.getDefaultServiceContext());
		if(JCollectionUtils.hasInCollect(appMetas)){
			for(AppMeta appMeta:appMetas){
				
				RemoteHttpComponentVersionApplication versionApplication=new RemoteHttpComponentVersionApplication();
				versionApplication.setApp(appMeta.getAppName());
				versionApplication.setComponent(appMeta.getAppCompName());
				versionApplication.setVersion(appMeta.getAppVersion());
				versionApplication.setUrlPrefix("");
				versionApplication.setFriendlyUrl(appMeta.getFriendlyUrl());
				
				JRemoteHttpContainerConfig remoteHttpContainerConfig=new JRemoteHttpContainerConfig();
				remoteHttpContainerConfig.setName(versionApplication.getApp());
				remoteHttpContainerConfig.setUnique(versionApplication.unique());
				remoteHttpContainerConfig.setHost(appMeta.getAppHost());
				String unique=requestInvokeContainerDelegateService.newInstance(remoteHttpContainerConfig);
				LOGGER.info(appMeta.getAppUnique()+" has startup with the unique id ["+unique+"]");
				
			}
		}
		return true;
	}

}
