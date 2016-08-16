package com.youappcorp.project.resourcemanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import j.jave.platform.jpa.springjpa.query.JQuery;
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
import com.youappcorp.project.resourcemanager.model.Resource;
import com.youappcorp.project.resourcemanager.model.ResourceGroup;
import com.youappcorp.project.resourcemanager.model.ResourceRecord;
import com.youappcorp.project.resourcemanager.model.ResourceRole;
import com.youappcorp.project.resourcemanager.vo.ResourceSearchCriteria;

@Service(value="resourceManagerServiceImpl.transation.jpa")
public class ResourceManagerServiceImpl extends ServiceSupport
implements ResourceManagerService {

	@Autowired
	private InternalResourceServiceImpl internalResourceServiceImpl;
	
	@Autowired
	private InternalResourceRoleServiceImpl internalResourceRoleServiceImpl;
	
	@Autowired
	private InternalResourceGroupServiceImpl internalResourceGroupServiceImpl;
	
	private JQuery<?> buildResourceQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a. id as id"
				+ ", a.url as url"
				+ ", a.friendlyUrl as friendlyUrl"
				+ ", a.cached as cached "
				+ ", a.description as description"
				+ " from Resource a "
				+ " where a.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("id"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :id";
		}
		if((condition=params.get("url"))!=null){
			jpql=jpql+ " and a.url "+condition.getOpe()+" :url";
		}
		if((condition=params.get("friendlyUrl"))!=null){
			jpql=jpql+ " and a.friendlyUrl "+condition.getOpe()+" :friendlyUrl";
		}
		if((condition=params.get("cached"))!=null){
			jpql=jpql+ " and a.cached "+condition.getOpe()+" :cached";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+ " and a.description "+condition.getOpe()+" :description";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}
	
	
	@Override
	public List<ResourceRecord> getResources(ServiceContext serviceContext) {
		return buildResourceQuery(serviceContext, Collections.EMPTY_MAP)
				.models(ResourceRecord.class);
	}
	
	@Override
	public List<ResourceRecord> getResources(ServiceContext serviceContext,
			ResourceSearchCriteria resourceSearchCriteria) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getUrl())){
			params.put("url", Condition.likes(resourceSearchCriteria.getUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getFriendlyUrl())){
			params.put("friendlyUrl", Condition.likes(resourceSearchCriteria.getFriendlyUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getCached())){
			params.put("cached", Condition.equal(resourceSearchCriteria.getCached()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getDescription())){
			params.put("description", Condition.likes(resourceSearchCriteria.getDescription()));
		}
		return buildResourceQuery(serviceContext, params)
				.models(ResourceRecord.class);
	}
	
	@Override
	public ResourceRecord getResourceByURL(ServiceContext serviceContext, String url) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("url", Condition.equal(url));
		return buildResourceQuery(serviceContext, params)
				.model(ResourceRecord.class);
	}
	
	@Override
	public boolean existsResourceByUrl(ServiceContext serviceContext, String url) {
		return internalResourceServiceImpl.singleEntityQuery().conditionDefault()
				.equals("url",url).ready().count()>0;
	}
	
	@Override
	public void saveResource(ServiceContext serviceContext, Resource resource) throws BusinessException{
		try{
			Resource dbResource=internalResourceServiceImpl.singleEntityQuery().conditionDefault()
					.equals("url",resource.getUrl()).ready().model();
			if(dbResource!=null){
				throw new BusinessException("duplicate url : "+resource.getUrl());
			}
			internalResourceServiceImpl.saveOnly(serviceContext, resource);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void enableCache(ServiceContext serviceContext, String resourceId)
			throws BusinessException {
		try{
			Resource dbResource=internalResourceServiceImpl.getById(serviceContext, resourceId);
			dbResource.setCached("Y");
			internalResourceServiceImpl.updateOnly(serviceContext, dbResource);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void disableCache(ServiceContext serviceContext, String resourceId)
			throws BusinessException {
		try{
			Resource dbResource=internalResourceServiceImpl.getById(serviceContext, resourceId);
			dbResource.setCached("N");
			internalResourceServiceImpl.updateOnly(serviceContext, dbResource);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public List<ResourceRole> getResourceRolesByResourceId(
			ServiceContext serviceContext, String resourceId) {
		return internalResourceRoleServiceImpl.singleEntityQuery()
				.conditionDefault().equals("resourceId", resourceId)
				.ready().models();
	}

	@Override
	public long countOnResourceIdAndRoleId(ServiceContext serviceContext,
			String resourceId, String roleId) {
		return internalResourceRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("resourceId", resourceId)
				.equals("roleId", roleId)
				.ready().count();
	}

	@Override
	public ResourceRole getResourceRoleOnResourceIdAndRoleId(
			ServiceContext serviceContext, String resourceId, String roleId) {
		return internalResourceRoleServiceImpl.singleEntityQuery().conditionDefault()
				.equals("resourceId", resourceId)
				.equals("roleId", roleId)
				.ready().model();
	}
	
	
	@Override
	public ResourceRole bingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) throws BusinessException {
		ResourceRole resourceRole=null;
		try{

			if(isBingResourceRole(serviceContext, resourceId, roleId)){
				throw new BusinessException("the resource had already belong to the role.");
			}
			
			resourceRole=new ResourceRole();
			resourceRole.setResourceId(resourceId);
			resourceRole.setRoleId(roleId);
			resourceRole.setId(JUniqueUtils.unique());
			internalResourceRoleServiceImpl.saveOnly(serviceContext, resourceRole);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceRole;
	}
	
	@Override
	public ResourceRole bingResourceRoleByUrl(ServiceContext serviceContext,
			String url, String roleId) throws BusinessException {
		ResourceRole resourceRole=null;
		try{
			Resource resource= getResourceByURL(serviceContext, url);
			if(resource==null){
				throw new BusinessException("the url is missing.");
			}
			resourceRole= bingResourceRole(serviceContext, resource.getId(), roleId);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceRole;
	}
	
	@Override
	public void unbingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) throws BusinessException {
		try{
			ResourceRole resourceRole=getResourceRoleOnResourceIdAndRoleId(serviceContext, resourceId, roleId);
			if(resourceRole==null){
				throw new BusinessException("the resource had not already belong to the role.");
			}
			internalResourceRoleServiceImpl.delete(serviceContext, resourceRole);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean isBingResourceRole(ServiceContext serviceContext, String resourceId,
			String roleId) {
		long count=countOnResourceIdAndRoleId(serviceContext,resourceId, roleId);
		return count>0;
	}
	
	
	
	@Override
	public List<ResourceGroup> getResourceGroupsByResourceId(
			ServiceContext serviceContext, String resourceId) {
		return internalResourceGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("resourceId", resourceId)
				.ready().models();
	}

	@Override
	public long countOnResourceIdAndGroupId(ServiceContext serviceContext,
			String resourceId, String groupId) {
		return internalResourceGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("resourceId", resourceId)
				.equals("groupId", groupId)
				.ready().count();
	}

	@Override
	public ResourceGroup getResourceGroupOnResourceIdAndGroupId(
			ServiceContext serviceContext, String resourceId, String groupId) {
		return internalResourceGroupServiceImpl.singleEntityQuery().conditionDefault()
				.equals("resourceId", resourceId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	
	@Override
	public ResourceGroup bingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) throws BusinessException {
		ResourceGroup resourceGroup=null;
		try{
			if(isBingResourceGroup(serviceContext, resourceId, groupId)){
				throw new BusinessException("the resource had already belong to the group.");
			}
			resourceGroup=new ResourceGroup();
			resourceGroup.setResourceId(resourceId);
			resourceGroup.setGroupId(groupId);
			resourceGroup.setId(JUniqueUtils.unique());
			internalResourceGroupServiceImpl.saveOnly(serviceContext, resourceGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceGroup;
	}
	
	@Override
	public ResourceGroup bingResourceGroupByUrl(ServiceContext serviceContext,
			String url, String groupId) throws BusinessException {
		ResourceGroup resourceGroup=null;
		try{
			Resource resource= getResourceByURL(serviceContext, url);
			if(resource==null){
				throw new BusinessException("the url is missing.");
			}
			resourceGroup=bingResourceGroup(serviceContext, resource.getId(), groupId);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		return resourceGroup;
		
	}
	
	@Override
	public void unbingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) throws BusinessException {
		try{
			ResourceGroup resourceGroup=getResourceGroupOnResourceIdAndGroupId(serviceContext, resourceId, groupId);
			if(resourceGroup==null){
				throw new BusinessException("the resource had not already belong to the group.");
			}
			internalResourceGroupServiceImpl.delete(serviceContext, resourceGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public boolean isBingResourceGroup(ServiceContext serviceContext, String resourceId,
			String groupId) {
		long count=countOnResourceIdAndGroupId(serviceContext,resourceId, groupId);
		return count>0;
	}


	@Override
	public JPage<ResourceRecord> getResourcesByPage(
			ServiceContext serviceContext,
			ResourceSearchCriteria resourceSearchCriteria,
			JSimplePageable simplePageable) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getUrl())){
			params.put("url", Condition.likes(resourceSearchCriteria.getUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getFriendlyUrl())){
			params.put("friendlyUrl", Condition.likes(resourceSearchCriteria.getFriendlyUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getCached())){
			params.put("cached", Condition.equal(resourceSearchCriteria.getCached()));
		}
		if(JStringUtils.isNotNullOrEmpty(resourceSearchCriteria.getDescription())){
			params.put("description", Condition.likes(resourceSearchCriteria.getDescription()));
		}
		return buildResourceQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(ResourceRecord.class);
	}


	@Override
	public ResourceRecord getResourceById(ServiceContext serviceContext,
			String id) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildResourceQuery(serviceContext, params)
				.model(ResourceRecord.class);
	}
	
	
	
}
