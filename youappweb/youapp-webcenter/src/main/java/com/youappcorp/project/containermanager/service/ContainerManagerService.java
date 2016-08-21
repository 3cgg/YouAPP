package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.AppMetaRecord;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaRecord;
import com.youappcorp.project.containermanager.vo.AppMetaCriteria;
import com.youappcorp.project.containermanager.vo.URLMappingMetaCriteria;

public interface ContainerManagerService {

	/**
	 * save a new APP.
	 * @param serviceContext
	 * @param appMeta
	 */
	void saveAppMeta(ServiceContext serviceContext,AppMeta appMeta);
	
	void saveAppMeta(ServiceContext serviceContext,AppMeta appMeta,List<URLMappingMeta> urlMappingMetas);
	
	void saveURLMappingMetas(ServiceContext serviceContext,List<URLMappingMeta> urlMappingMetas);
	
	void saveURLMappingMeta(ServiceContext serviceContext,URLMappingMeta urlMappingMeta);
	
	void updateAppMeta(ServiceContext serviceContext,AppMeta appMeta,List<URLMappingMeta> urlMappingMetas);
	
	
	/**
	 * 
	 * query APP
	 * @param serviceContext
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	AppMetaRecord getAPPMetaByConfig(ServiceContext serviceContext,String appName,String appCompName,String appVersion);

	AppMetaRecord getAppMetaByUnique(ServiceContext serviceContext,String unique);
	
	/**
	 * 
	 * @param serviceContext
	 * @param appId
	 * @return
	 */
	AppMetaRecord getAPPMetaByAppId(ServiceContext serviceContext,String appId);
	
	/**
	 * check if the app already exists.
	 * @param serviceContext
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	boolean existsAppMeta(ServiceContext serviceContext,String appName,String appCompName,String appVersion);
	
	boolean existsAppMeta(ServiceContext serviceContext,String unique);
	
	/**
	 * query all app-metas.
	 * @param serviceContext
	 * @return
	 */
	List<AppMetaRecord> getAppMetas(ServiceContext serviceContext);
	
	/**
	 * query all app-metas.
	 * @param serviceContext
	 * @return
	 */
	List<AppMetaRecord> getAppMetas(ServiceContext serviceContext,AppMetaCriteria appMetaCriteria);
	
	
	/**
	 * query all app-metas.
	 * @param serviceContext
	 * @return
	 */
	JPage<AppMetaRecord> getAppMetasByPage(ServiceContext serviceContext,AppMetaCriteria appMetaCriteria,JSimplePageable simplePageable);
	
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * @param serviceContext
	 * @return
	 */
	List<URLMappingMetaRecord> getURLMappingMetasByAppId(ServiceContext serviceContext,String appId);
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * @param serviceContext
	 * @return
	 */
	List<URLMappingMetaRecord> getURLMappingMetasByAppConfig(ServiceContext serviceContext,String appName,String appCompName,String appVersion);
	
	
	URLMappingMetaRecord getURLMappingMetaById(ServiceContext serviceContext,String id);
	
	/**
	 * 
	 * @param serviceContext
	 * @param urlMappingMetaCriteria
	 * @return
	 */
	JPage<URLMappingMetaRecord> getURLMappingMetasByPage(ServiceContext serviceContext,URLMappingMetaCriteria urlMappingMetaCriteria,JSimplePageable simplePageable);

	
}
