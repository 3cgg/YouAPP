package com.youappcorp.project.containermanager.service;

import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.containermanager.model.AppMeta;

public interface ContainerManagerService {

	/**
	 * save a new APP.
	 * @param context
	 * @param appMeta
	 */
	void saveAppMeta(ServiceContext context,AppMeta appMeta) throws BusinessException;
	
	
	/**
	 * 
	 * query APP
	 * @param context
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	AppMeta getAPPMetaByConfig(ServiceContext context,String appName,String appCompName,String appVersion);

	
	/**
	 * query all app-metas.
	 * @param context
	 * @return
	 */
	List<AppMeta> getAllAppMetas(ServiceContext context);
	
}
