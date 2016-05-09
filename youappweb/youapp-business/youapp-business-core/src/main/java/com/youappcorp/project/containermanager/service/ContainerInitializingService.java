package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.logging.JLogger;
import j.jave.kernal.jave.logging.JLoggerFactory;
import j.jave.kernal.jave.service.JService;
import j.jave.kernal.jave.utils.JCollectionUtils;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedEvent;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringApplicationContextInitializedListener;
import j.jave.platform.basicsupportcomp.core.servicehub.SpringServiceFactorySupport;
import j.jave.platform.basicwebcomp.core.service.DefaultServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.containermanager.model.AppMeta;

@Service
public class ContainerInitializingService
extends SpringServiceFactorySupport<ContainerInitializingService>
implements SpringApplicationContextInitializedListener ,JService {

	private final static JLogger LOGGER=JLoggerFactory.getLogger(ContainerInitializingService.class);
	
	
	@Autowired
	private ContainerManagerService containerManagerService;
	
	@Override
	public Object trigger(SpringApplicationContextInitializedEvent event) {
		
		List<AppMeta> appMetas= containerManagerService.getAllAppMetas(DefaultServiceContext.getDefaultServiceContext());
		if(JCollectionUtils.hasInCollect(appMetas)){
			for(AppMeta appMeta:appMetas){
				LOGGER.info(appMeta.getUnique());
			}
		}
		return true;
	}

}
