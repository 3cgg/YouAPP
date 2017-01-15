package com.youappcorp.project.containermanager.service;

import j.jave.platform.webcomp.core.service.ServiceSupport;
import me.bunny.app._c.jpa.springjpa.query.JQuery;
import me.bunny.app._c.jpa.springjpa.query.JCondition.Condition;
import me.bunny.app._c.sps.multiv.jnterface.JKey;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.utils.JStringUtils;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.containermanager.jpa.AppMetaJPARepo;
import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.AppMetaRecord;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaRecord;
import com.youappcorp.project.containermanager.vo.AppMetaCriteria;
import com.youappcorp.project.containermanager.vo.URLMappingMetaCriteria;

@Service(value="containerManagerService.transation.jpa")
public class ContainerManagerServiceImpl extends ServiceSupport  implements ContainerManagerService {

	@Autowired
	private InternalAppMetaServiceImpl internalAppMetaServiceImpl;
	
	@Autowired
	private InternalURLMappingMetaServiceImpl internalURLMappingMetaServiceImpl;
	
	@Autowired
	private AppMetaJPARepo appMetaRepo;
	
	@Override
	public void saveAppMeta( AppMeta appMeta) throws BusinessException {
		try{
			if(existsAppMeta( appMeta.getAppName(), appMeta.getAppCompName(), appMeta.getAppVersion())){
				throw new BusinessException("the app already exists.");
			}
			internalAppMetaServiceImpl.saveOnly( appMeta);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void saveURLMappingMetas(List<URLMappingMeta> urlMappingMetas) {
		for(URLMappingMeta urlMappingMeta:urlMappingMetas){
			saveURLMappingMeta(urlMappingMeta);
		}
	}
	
	@Override
	public void saveURLMappingMeta(URLMappingMeta urlMappingMeta) {
		try{
			URLMappingMeta mappingMeta=getURLMappingMetaByUrl( urlMappingMeta.getUrl());
			if(mappingMeta!=null){
				throw new BusinessException("the url already exists.");
			}
			internalURLMappingMetaServiceImpl.saveOnly( urlMappingMeta);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void updateAppMeta( AppMeta appMeta,
			List<URLMappingMeta> urlMappingMetas) {
		AppMeta dbAppMeta=internalAppMetaServiceImpl.singleEntityQuery().conditionDefault()
				.equals("appName", appMeta.getAppName())
				.equals("appCompName", appMeta.getAppCompName())
				.equals("appVersion", appMeta.getAppVersion()).ready().model();
		
		List<URLMappingMeta> dbUrlMappingMetas= internalURLMappingMetaServiceImpl.singleEntityQuery()
		.conditionDefault().equals("appId", dbAppMeta.getId())
		.ready().models();
		
		for(URLMappingMeta urlMappingMeta:dbUrlMappingMetas){
			internalURLMappingMetaServiceImpl.delete( urlMappingMeta);
		}
		internalAppMetaServiceImpl.delete( dbAppMeta);
		
		saveAppMeta( appMeta, urlMappingMetas);
	}
	
	private URLMappingMeta getURLMappingMetaByUrl(String url){
		return internalURLMappingMetaServiceImpl.singleEntityQuery().conditionDefault()
				.equals("url", url)
				.ready().model();
	}
	
	
	@Override
	public void saveAppMeta( AppMeta appMeta,
			List<URLMappingMeta> urlMappingMetas) {
		saveAppMeta( appMeta);
		for(URLMappingMeta urlMappingMeta:urlMappingMetas){
			urlMappingMeta.setAppId(appMeta.getId());
			saveURLMappingMeta(urlMappingMeta);
		}
	}
	
	private JQuery<?> buildAPPMetaQuery(Map<String, Condition> params){
		
		String jpql="select a.id as id "
				+ ", a.appName as appName"
				+ ", a.appCompName as appCompName"
				+ ", a.appVersion as appVersion"
				+ ", a.appUnique as appUnique"
				+ ", a.appDesc as appDesc"
				+ ", a.appActive as appActive"
				+ ", a.appHost as appHost"
				+ ", a.friendlyUrl as friendlyUrl"
				+ ", a.appJarUrl as appJarUrl"
				+ ", a.deployType as deployType"
				+ " from AppMeta a "
				+ " where a.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("id"))!=null){
			jpql=jpql+" and a.id "+condition.getOpe()+" :id  ";
		}
		if((condition=params.get("appName"))!=null){
			jpql=jpql+" and a.appName "+condition.getOpe()+" :appName  ";
		}
		if((condition=params.get("appCompName"))!=null){
			jpql=jpql+" and a.appCompName "+condition.getOpe()+" :appCompName  ";
		}
		
		if((condition=params.get("appVersion"))!=null){
			jpql=jpql+" and a.appVersion "+condition.getOpe()+" :appVersion  ";
		}
		if((condition=params.get("appActive"))!=null){
			jpql=jpql+" and a.appActive "+condition.getOpe()+" :appActive  ";
		}
		if((condition=params.get("appHost"))!=null){
			jpql=jpql+" and a.appHost "+condition.getOpe()+" :appHost  ";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	@Override
	public AppMetaRecord getAPPMetaByConfig( String appName,
			String appCompName, String appVersion) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("appName", Condition.equal(appName));
		params.put("appCompName", Condition.equal(appCompName));
		params.put("appVersion", Condition.equal(appVersion));		
		return buildAPPMetaQuery( params)
				.model(AppMetaRecord.class);
	}
	
	@Override
	public AppMetaRecord getAppMetaByUnique(
			String unique) {
		JKey key=JKey.parse(unique);
		String appName=key.getApp();
		String appCompName=key.getComponent();
		String appVersion=key.getVersion();
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("appName", Condition.equal(appName));
		params.put("appCompName", Condition.equal(appCompName));
		params.put("appVersion", Condition.equal(appVersion));		
		return buildAPPMetaQuery( params)
				.model(AppMetaRecord.class);
	}
	

	@Override
	public List<AppMetaRecord> getAppMetas() {
		return buildAPPMetaQuery( Collections.EMPTY_MAP).models(AppMetaRecord.class);
	}
	
	@Override
	public List<AppMetaRecord> getAppMetas(
			AppMetaCriteria appMetaCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppName())){
			params.put("appName", Condition.equal(appMetaCriteria.getAppName()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppCompName())){
			params.put("appCompName", Condition.equal(appMetaCriteria.getAppCompName()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppVersion())){
			params.put("appVersion", Condition.equal(appMetaCriteria.getAppVersion()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppActive())){
			params.put("appActive", Condition.equal(appMetaCriteria.getAppActive()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppHost())){
			params.put("appHost", Condition.equal(appMetaCriteria.getAppHost()));
		}
		return buildAPPMetaQuery( params)
				.models(AppMetaRecord.class);
	}

	@Override
	public JPage<AppMetaRecord> getAppMetasByPage(
			AppMetaCriteria appMetaCriteria, JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppName())){
			params.put("appName", Condition.likes(appMetaCriteria.getAppName()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppCompName())){
			params.put("appCompName", Condition.likes(appMetaCriteria.getAppCompName()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppVersion())){
			params.put("appVersion", Condition.likes(appMetaCriteria.getAppVersion()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppActive())){
			params.put("appActive", Condition.equal(appMetaCriteria.getAppActive()));
		}
		if(JStringUtils.isNotNullOrEmpty(appMetaCriteria.getAppHost())){
			params.put("appHost", Condition.likes(appMetaCriteria.getAppHost()));
		}
		return buildAPPMetaQuery( params)
				.setPageable(simplePageable)
				.modelPage(AppMetaRecord.class);
	}
	
	
	private JQuery<?> buildURLMappingMetaQuery(Map<String, Condition> params){
		
		String jpql="select a.id as id "
				+ ", a.appId as appId"
				+ ", a.url as url"
				+ ", a.urlType as urlType"
				+ ", a.urlDesc as urlDesc"
				+ ", a.urlName as urlName"
				+ ", a.urlActive as urlActive"
				+ " from URLMappingMeta a "
				+ " left join AppMeta b on a.appId=b.id "
				+ " where a.deleted='N' and b.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("id"))!=null){
			jpql=jpql+" and a.id "+condition.getOpe()+" :id  ";
		}
		if((condition=params.get("appId"))!=null){
			jpql=jpql+" and a.appId "+condition.getOpe()+" :appId  ";
		}
		if((condition=params.get("url"))!=null){
			jpql=jpql+" and a.url "+condition.getOpe()+" :url  ";
		}
		
		if((condition=params.get("urlType"))!=null){
			jpql=jpql+" and a.urlType "+condition.getOpe()+" :urlType  ";
		}
		if((condition=params.get("urlDesc"))!=null){
			jpql=jpql+" and a.urlDesc "+condition.getOpe()+" :urlDesc  ";
		}
		if((condition=params.get("urlName"))!=null){
			jpql=jpql+" and a.urlName "+condition.getOpe()+" :urlName  ";
		}
		if((condition=params.get("urlActive"))!=null){
			jpql=jpql+" and a.urlActive "+condition.getOpe()+" :urlActive  ";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}

	@Override
	public List<URLMappingMetaRecord> getURLMappingMetasByAppId(String appId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("appId", Condition.equal(appId));
		return buildURLMappingMetaQuery( params)
				.models(URLMappingMetaRecord.class);
	}
	
	@Override
	public List<URLMappingMetaRecord> getURLMappingMetasByAppConfig(
			 String appName, String appCompName,
			String appVersion) {
		AppMeta appMeta=getAPPMetaByConfig( appName, appCompName, appVersion);
		if(appMeta==null) return Collections.EMPTY_LIST;
		return getURLMappingMetasByAppId( appMeta.getId());
	}
	
	@Override
	public AppMetaRecord getAPPMetaByAppId( String appId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(appId));
		return buildAPPMetaQuery( params)
				.model(AppMetaRecord.class);
	}
	
	@Override
	public URLMappingMetaRecord getURLMappingMetaById(
			String id) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildURLMappingMetaQuery( params)
				.model(URLMappingMetaRecord.class);
	}
	
	
	
	@Override
	public JPage<URLMappingMetaRecord> getURLMappingMetasByPage(
			
			URLMappingMetaCriteria urlMappingMetaCriteria,JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(urlMappingMetaCriteria.getAppId())){
			params.put("appId", Condition.likes(urlMappingMetaCriteria.getAppId()));
		}
		if(JStringUtils.isNotNullOrEmpty(urlMappingMetaCriteria.getUrl())){
			params.put("url", Condition.likes(urlMappingMetaCriteria.getUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(urlMappingMetaCriteria.getUrlDesc())){
			params.put("urlDesc", Condition.likes(urlMappingMetaCriteria.getUrlDesc()));
		}
		return buildURLMappingMetaQuery( params)
		.setPageable(simplePageable)
		.modelPage(URLMappingMetaRecord.class);
	}
	
	@Override
	public boolean existsAppMeta(
			String appName, String appCompName, String appVersion) {
		long count=internalAppMetaServiceImpl.singleEntityQuery().conditionDefault()
				.equals("appName", appName)
				.equals("appCompName", appCompName)
				.equals("appVersion", appVersion)
				.ready().count();
		return count>0;
	}
	
	@Override
	public boolean existsAppMeta( String unique) {
		JKey key=JKey.parse(unique);
		String appName=key.getApp();
		String appCompName=key.getComponent();
		String appVersion=key.getVersion();
		long count=internalAppMetaServiceImpl.singleEntityQuery().conditionDefault()
				.equals("appName", appName)
				.equals("appCompName", appCompName)
				.equals("appVersion", appVersion)
				.ready().count();
		return count>0;
	}
	
	
	
}
