package com.youappcorp.project.containermanager.service;

import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.repo.AppMetaRepo;

@Service(value="containerManagerService.transation.jpa")
public class ContainerManagerServiceImpl extends ServiceSupport  implements ContainerManagerService {

	@Autowired
	private InternalAppMetaServiceImpl internalAppMetaServiceImpl;
	
	@Autowired
	private InternalURLMappingMetaServiceImpl internalURLMappingMetaServiceImpl;
	
	@Autowired
	private AppMetaRepo appMetaRepo;
	
	@Override
	public void saveAppMeta(ServiceContext context, AppMeta appMeta) throws BusinessException {
		try{
			if(exists(appMeta)){
				throw new BusinessException("the app already exists.");
			}
			internalAppMetaServiceImpl.saveOnly(context, appMeta);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	private boolean exists(AppMeta appMeta){
		long count=appMetaRepo.getCountByConfig(appMeta.getAppName(),
				appMeta.getAppCompName(), appMeta.getAppVersion());
		return count>0;
	}
	
	@Override
	public AppMeta getAPPMetaByConfig(ServiceContext context, String appName,
			String appCompName, String appVersion) {
		return appMetaRepo.getAppMetaByConfig(appName, appCompName, appVersion);
	}

	@Override
	public List<AppMeta> getAllAppMetas(ServiceContext context) {
		return appMetaRepo.getAllModels();
	}

}
