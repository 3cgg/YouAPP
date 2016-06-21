package com.youappcorp.project.containermanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.containermanager.model.AppMeta;
import com.youappcorp.project.containermanager.model.URLMappingMeta;
import com.youappcorp.project.containermanager.model.URLMappingMetaCriteria;
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
			if(exists(context, appMeta.getAppName(), appMeta.getAppCompName(), appMeta.getAppVersion())){
				throw new BusinessException("the app already exists.");
			}
			internalAppMetaServiceImpl.saveOnly(context, appMeta);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	private boolean exists(ServiceContext context, String appName,
			String appCompName, String appVersion){
		long count=appMetaRepo.getCountByConfig(appName, appCompName, appVersion);
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

	@Override
	public boolean existsAppMeta(ServiceContext context, String appName,
			String appCompName, String appVersion) {
		return exists(context, appName, appCompName, appVersion);
	}
	
	@Override
	public List<URLMappingMeta> getAllURLMappingMetasByAppId(ServiceContext context,String appId) {
		String jpql="from URLMappingMeta where appId= :appId and deleted=1";
		Map<String, Object> params=new HashMap<String, Object>();
		params.put("appId", appId);
		return queryBuilder()
		.jpqlQuery().setJpql(jpql)
		.setParams(params)
		.models();
	}
	
	@Override
	public List<URLMappingMeta> getAllURLMappingMetasByAppConfig(
			ServiceContext context, String appName, String appCompName,
			String appVersion) {
		AppMeta appMeta=getAPPMetaByConfig(context, appName, appCompName, appVersion);
		if(appMeta==null) return Collections.EMPTY_LIST;
		return getAllURLMappingMetasByAppId(context, appMeta.getId());
	}
	
	@Override
	public AppMeta getAPPMetaByAppId(ServiceContext context, String appId) {
		return internalAppMetaServiceImpl.getById(context, appId);
	}
	
	@Override
	public URLMappingMeta getURLMappingMetaById(ServiceContext context,
			String id) {
		return internalURLMappingMetaServiceImpl.getById(context, id);
	}
	
	
	
	@Override
	public JPage<URLMappingMeta> getAllURLMappingMetasByPage(
			ServiceContext context,
			URLMappingMetaCriteria urlMappingMetaCriteria) {
		StringBuffer nativeSql=new StringBuffer(
				"select a.* from UrlMappingMeta a left join AppMeta b on a.APP_ID=b.ID  "
				+ " where 1=1 ");
		Map<String, Object> params=new HashMap<String, Object>();
		if(JStringUtils.isNotNullOrEmpty(urlMappingMetaCriteria.getUrl())){
			nativeSql.append(" and a.URL like :url");
			params.put("url", "%"+urlMappingMetaCriteria.getUrl()+"%");
		}
		if(JStringUtils.isNotNullOrEmpty(urlMappingMetaCriteria.getUrlDesc())){
			nativeSql.append(" and a.URL_DESC like :urlDesc");
			params.put("urlDesc", "%"+urlMappingMetaCriteria.getUrlDesc()+"%");
		}
		
		return queryBuilder()
		.nativeQuery().setSql(nativeSql.toString())
		.setParams(params)
		.setPageable(urlMappingMetaCriteria)
		.modelPage();
		
	}
}
