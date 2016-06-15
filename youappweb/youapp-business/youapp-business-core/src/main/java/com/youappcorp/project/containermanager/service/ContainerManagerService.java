package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaCriteria;

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
	 * 
	 * @param context
	 * @param appId
	 * @return
	 */
	AppMeta getAPPMetaByAppId(ServiceContext context,String appId);
	
	/**
	 * check if the app already exists.
	 * @param context
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	boolean existsAppMeta(ServiceContext context,String appName,String appCompName,String appVersion);
	
	/**
	 * query all app-metas.
	 * @param context
	 * @return
	 */
	List<AppMeta> getAllAppMetas(ServiceContext context);
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * @param context
	 * @return
	 */
	List<URLMappingMeta> getAllURLMappingMetasByAppId(ServiceContext context,String appId);
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * @param context
	 * @return
	 */
	List<URLMappingMeta> getAllURLMappingMetasByAppConfig(ServiceContext context,String appName,String appCompName,String appVersion);
	
	
	URLMappingMeta getURLMappingMetaById(ServiceContext context,String id);
	
	/**
	 * 
	 * @param context
	 * @param urlMappingMetaCriteria
	 * @return
	 */
	JPage<URLMappingMeta> getAllURLMappingMetasByPage(ServiceContext context,URLMappingMetaCriteria urlMappingMetaCriteria);
	
	
	
}
