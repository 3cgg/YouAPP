package com.youappcorp.project.containermanager.service;

import java.util.List;

import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.AppMetaRecord;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaRecord;
import com.youappcorp.project.containermanager.vo.AppMetaCriteria;
import com.youappcorp.project.containermanager.vo.URLMappingMetaCriteria;

import me.bunny.kernel.jave.model.JPage;
import me.bunny.kernel.jave.model.JSimplePageable;

public interface ContainerManagerService {

	/**
	 * save a new APP.
	 * 
	 * @param appMeta
	 */
	void saveAppMeta(AppMeta appMeta);
	
	void saveAppMeta(AppMeta appMeta,List<URLMappingMeta> urlMappingMetas);
	
	void saveURLMappingMetas(List<URLMappingMeta> urlMappingMetas);
	
	void saveURLMappingMeta(URLMappingMeta urlMappingMeta);
	
	void updateAppMeta(AppMeta appMeta,List<URLMappingMeta> urlMappingMetas);
	
	
	/**
	 * 
	 * query APP
	 * 
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	AppMetaRecord getAPPMetaByConfig(String appName,String appCompName,String appVersion);

	AppMetaRecord getAppMetaByUnique(String unique);
	
	/**
	 * 
	 * 
	 * @param appId
	 * @return
	 */
	AppMetaRecord getAPPMetaByAppId(String appId);
	
	/**
	 * check if the app already exists.
	 * 
	 * @param appName
	 * @param appCompName
	 * @param appVersion
	 * @return
	 */
	boolean existsAppMeta(String appName,String appCompName,String appVersion);
	
	boolean existsAppMeta(String unique);
	
	/**
	 * query all app-metas.
	 * 
	 * @return
	 */
	List<AppMetaRecord> getAppMetas();
	
	/**
	 * query all app-metas.
	 * 
	 * @return
	 */
	List<AppMetaRecord> getAppMetas(AppMetaCriteria appMetaCriteria);
	
	
	/**
	 * query all app-metas.
	 * 
	 * @return
	 */
	JPage<AppMetaRecord> getAppMetasByPage(AppMetaCriteria appMetaCriteria,JSimplePageable simplePageable);
	
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * 
	 * @return
	 */
	List<URLMappingMetaRecord> getURLMappingMetasByAppId(String appId);
	
	/**
	 * QUERUY ALL URL MAPPINGS of a certain APP.
	 * 
	 * @return
	 */
	List<URLMappingMetaRecord> getURLMappingMetasByAppConfig(String appName,String appCompName,String appVersion);
	
	
	URLMappingMetaRecord getURLMappingMetaById(String id);
	
	/**
	 * 
	 * 
	 * @param urlMappingMetaCriteria
	 * @return
	 */
	JPage<URLMappingMetaRecord> getURLMappingMetasByPage(URLMappingMetaCriteria urlMappingMetaCriteria,JSimplePageable simplePageable);

	
}
