package com.youappcorp.project.containermanager.service;

import j.jave.kernal.eventdriven.servicehub.JServiceHubDelegate;
import j.jave.kernal.jave.exception.JInitializationException;
import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedEvent;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedListener;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;
import j.jave.platform.basicwebcomp.rhttp.DefaultRemoteHttpDeployService;
import j.jave.platform.basicwebcomp.rhttp.model.AppDeploy;
import j.jave.platform.basicwebcomp.rhttp.model.AppDeployMeta;
import j.jave.platform.basicwebcomp.rhttp.model.URLMappingDeployMeta;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.URLMappingMeta;

@Service
public class ContainerInitializingService
extends SpringServiceFactorySupport<ContainerInitializingService>
implements SpringApplicationContextInitializedListener ,JService {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(ContainerInitializingService.class);
	
	private DefaultRemoteHttpDeployService defaultRemoteHttpDeployService=
			JServiceHubDelegate.get().getService(this,DefaultRemoteHttpDeployService.class);
	
	
	@Autowired
	private ContainerManagerService containerManagerService;
	
	@Override
	public Object trigger(SpringApplicationContextInitializedEvent event) {
		try{
			List<AppMeta> appMetas= containerManagerService.getAllAppMetas(DefaultServiceContext.getDefaultServiceContext());
			if(JCollectionUtils.hasInCollect(appMetas)){
				for(AppMeta appMeta:appMetas){
					AppDeploy appDeploy=new AppDeploy();
					
					//initialize app meta
					AppDeployMeta appDeployMeta=new AppDeployMeta();
					appDeployMeta.setAppName(appMeta.getAppName());
					appDeployMeta.setAppCompName(appMeta.getAppCompName());
					appDeployMeta.setAppVersion(appMeta.getAppVersion());
					appDeployMeta.setAppActive(appMeta.getAppActive());
					appDeployMeta.setAppHost(appMeta.getAppHost());
					appDeployMeta.setAppDesc(appMeta.getAppDesc());
					appDeployMeta.setAppUnique(appMeta.getAppUnique());
					appDeployMeta.setFriendlyUrl(appMeta.getFriendlyUrl());
					appDeploy.setAppMeta(appDeployMeta);
					
					//initialize URL mapping
					List<URLMappingDeployMeta> urlMappingDeployMetas=new ArrayList<URLMappingDeployMeta>();
					List<URLMappingMeta> urlMappingMetas=containerManagerService.getAllURLMappingMetasByAppId(DefaultServiceContext.getDefaultServiceContext(), appMeta.getId());
					if(JCollectionUtils.hasInCollect(urlMappingMetas)){
						for(URLMappingMeta urlMappingMeta:urlMappingMetas){
							URLMappingDeployMeta urlMappingDeployMeta=new URLMappingDeployMeta();
							urlMappingDeployMeta.setUrl(urlMappingMeta.getUrl());
							urlMappingDeployMeta.setUrlActive(urlMappingMeta.getUrlActive());
							urlMappingDeployMeta.setUrlDesc(urlMappingMeta.getUrlDesc());
							urlMappingDeployMeta.setUrlName(urlMappingMeta.getUrlName());
							urlMappingDeployMeta.setUrlType(urlMappingMeta.getUrlType());
							urlMappingDeployMetas.add(urlMappingDeployMeta);
						}
					}
					appDeploy.setUrlMappingMetas(urlMappingDeployMetas);
					String unique=defaultRemoteHttpDeployService.deploy(appDeploy);
					LOGGER.info(appMeta.getAppUnique()+" has startup with the unique id ["+unique+"]");
				}
			}
			return true;
		}catch(Exception e){
			LOGGER.error(e.getMessage(), e);
			throw new JInitializationException(e);
		}
		
	}

}
