package com.youappcorp.project.menumanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.utils.JStringUtils;
import j.jave.platform.jpa.springjpa.query.JCondition.Condition;
import j.jave.platform.jpa.springjpa.query.JQuery;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.core.service.ServiceSupport;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.menumanager.model.Menu;
import com.youappcorp.project.menumanager.model.MenuGroup;
import com.youappcorp.project.menumanager.model.MenuGroupRecord;
import com.youappcorp.project.menumanager.model.MenuRecord;
import com.youappcorp.project.menumanager.model.MenuRole;
import com.youappcorp.project.menumanager.model.MenuRoleRecord;
import com.youappcorp.project.menumanager.vo.MenuCriteriaInVO;

@Service(value="menuServiceImpl.transation.jpa")
public class MenuManagerServiceImpl extends ServiceSupport implements MenuManagerService {

	@Autowired
	private InternalMenuServiceImpl internalMenuServiceImpl;
	
	@Autowired
	private InternalMenuRoleServiceImpl internalMenuRoleServiceImpl;
	
	@Autowired
	private InternalMenuGroupServiceImpl internalMenuGroupServiceImpl;
	
	@Override
	public void saveMenu(ServiceContext serviceContext, MenuRecord menuRecord) {
		try{
			Menu menu=menuRecord.toMenu();
			internalMenuServiceImpl.saveOnly(serviceContext, menu);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void updateMenu(ServiceContext serviceContext, MenuRecord menuRecord) {
		try{
			Menu menu=menuRecord.toMenu();
			Menu dbMenu=internalMenuServiceImpl.getById(serviceContext, menu.getId());
			dbMenu.setName(menu.getName());
			dbMenu.setUrl(menu.getUrl());
			dbMenu.setDescription(menu.getDescription());
			dbMenu.setSequence(menu.getSequence());
			internalMenuServiceImpl.updateOnly(serviceContext, dbMenu);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void deleteMenu(ServiceContext serviceContext, MenuRecord menuRecord) {
		internalMenuServiceImpl.delete(serviceContext, menuRecord.toMenu().getId());
	}

	@Override
	public void deleteMenuById(ServiceContext serviceContext, String id) {
		internalMenuServiceImpl.delete(serviceContext, id);
	}
	
	
	private JQuery<?> buildMenuQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a. id as id"
				+ ", a.pid as pid "
				+ ", a.url as url"
				+ ", a.code as code "
				+ ", a.name as name"
				+ ", a.sequence as sequence "
				+ ", a.description as description"
				+ " from Menu a "
				+ " where a.deleted='N' ";
		Condition condition=null;
		if((condition=params.get("id"))!=null){
			jpql=jpql+ " and a.id "+condition.getOpe()+" :id";
		}
		if((condition=params.get("url"))!=null){
			jpql=jpql+ " and a.url "+condition.getOpe()+" :url";
		}
		if((condition=params.get("name"))!=null){
			jpql=jpql+ " and a.name "+condition.getOpe()+" :name";
		}
		if((condition=params.get("description"))!=null){
			jpql=jpql+ " and a.description "+condition.getOpe()+" :description";
		}
		if((condition=params.get("sequence"))!=null){
			jpql=jpql+ " and a.sequence "+condition.getOpe()+" :sequence";
		}
		jpql=jpql+" order by a.pid , a.sequence asc";
		return queryBuilder().jpqlQuery().setJpql(jpql)
				.setParams(params(params));
	}

	@Override
	public MenuRecord getMenuById(ServiceContext serviceContext, String id) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("id", Condition.equal(id));
		return buildMenuQuery(serviceContext, params)
				.model(MenuRecord.class);
	}

	@Override
	public List<MenuRecord> getMenusByUserId(ServiceContext serviceContext,
			String userId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("userId", Condition.equal(userId));
		return buildMenuQuery(serviceContext, params)
				.models(MenuRecord.class);
	}

	@Override
	public JPage<MenuRecord> getMenus(ServiceContext serviceContext,
			MenuCriteriaInVO menuCriteriaInVO, JSimplePageable simplePageable) {
		Map<String, Condition> params = getParams(menuCriteriaInVO);
		return buildMenuQuery(serviceContext, params)
				.setPageable(simplePageable)
				.modelPage(MenuRecord.class);
	}

	private Map<String, Condition> getParams(MenuCriteriaInVO menuCriteriaInVO) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		if(JStringUtils.isNotNullOrEmpty(menuCriteriaInVO.getUrl())){
			params.put("url", Condition.likes(menuCriteriaInVO.getUrl()));
		}
		if(JStringUtils.isNotNullOrEmpty(menuCriteriaInVO.getName())){
			params.put("name", Condition.likes(menuCriteriaInVO.getName()));
		}
		if(JStringUtils.isNotNullOrEmpty(menuCriteriaInVO.getDescription())){
			params.put("description", Condition.likes(menuCriteriaInVO.getDescription()));
		}
		if(JStringUtils.isNotNullOrEmpty(menuCriteriaInVO.getUserId())){
			params.put("userId", Condition.likes(menuCriteriaInVO.getUserId()));
		}
		return params;
	}
	
	@Override
	public List<MenuRecord> getMenus(ServiceContext serviceContext,
			MenuCriteriaInVO menuCriteriaInVO) {
		Map<String, Condition> params = getParams(menuCriteriaInVO);
		return buildMenuQuery(serviceContext, params)
				.models(MenuRecord.class);
	}

	private MenuRole getMenuRole(ServiceContext serviceContext, String menuId,
			String roleId){
		return internalMenuRoleServiceImpl.singleEntityQuery()
				.conditionDefault()
				.equals("menuId", menuId)
				.equals("roleId", roleId)
				.ready().model();
	}
	
	
	private MenuGroup getMenuGroup(ServiceContext serviceContext, String menuId,
			String groupId){
		return internalMenuGroupServiceImpl.singleEntityQuery()
				.conditionDefault()
				.equals("menuId", menuId)
				.equals("groupId", groupId)
				.ready().model();
	}
	
	
	@Override
	public void bindMenuRole(ServiceContext serviceContext, String menuId,
			String roleId) {
		try{
			MenuRole dbMenuRole=getMenuRole(serviceContext, menuId, roleId);
			if(dbMenuRole!=null){
				internalMenuRoleServiceImpl.delete(serviceContext, dbMenuRole);
			}
			MenuRole menuRole=new MenuRole();
			menuRole.setMenuId(menuId);
			menuRole.setRoleId(roleId);
			internalMenuRoleServiceImpl.saveOnly(serviceContext, menuRole);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void bindMenuGroup(ServiceContext serviceContext, String menuId,
			String groupId) {
		try{
			MenuGroup dbMenuGroup=getMenuGroup(serviceContext, menuId, groupId);
			if(dbMenuGroup!=null){
				internalMenuGroupServiceImpl.delete(serviceContext, dbMenuGroup);
			}
			MenuGroup menuGroup=new MenuGroup();
			menuGroup.setMenuId(menuId);
			menuGroup.setGroupId(groupId);
			internalMenuGroupServiceImpl.saveOnly(serviceContext, menuGroup);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void unbindMenuRole(ServiceContext serviceContext, String menuId,
			String roleId) {
		try{
			MenuRole dbMenuRole=getMenuRole(serviceContext, menuId, roleId);
			if(dbMenuRole!=null){
				internalMenuRoleServiceImpl.delete(serviceContext, dbMenuRole);
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}

	@Override
	public void unbindMenuGroup(ServiceContext serviceContext, String menuId,
			String groupId) {
		try{
			MenuGroup dbMenuGroup=getMenuGroup(serviceContext, menuId, groupId);
			if(dbMenuGroup!=null){
				internalMenuGroupServiceImpl.delete(serviceContext, dbMenuGroup);
			}
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	private JQuery<?> buildBindMenuRoleQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as roleId"
				+ ",  a.roleName as roleName "
				+ ",  a.roleCode as roleCode"
				+ ",  a.description as roleDesc"
				+ ",  c.id as menuId "
				+ ",  c.name as menuName"
				+ "  from Role a "
				+ " , MenuRole b"
				+ " , Menu  c"
				+ " where a.deleted='N' and b.deleted='N' and c.deleted='N'  "
				+ " and a.id=b.roleId and b.menuId=c.id ";
		Condition condition=null;
		if((condition=params.get("menuId"))!=null){
			jpql=jpql+" and c.id =  :menuId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
		.setParams(params(params));
	}
	
	
	private JQuery<?> buildUnbindMenuRoleQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as roleId"
				+ "  from Role a "
				+ " , MenuRole b"
				+ " , Menu  c"
				+ " where a.deleted='N' and b.deleted='N' and c.deleted='N'  "
				+ " and a.id=b.roleId and b.menuId=c.id ";
		Condition condition=null;
		if((condition=params.get("menuId"))!=null){
			jpql=jpql+" and c.id =  :menuId";
		}
		
		jpql="select o.id as roleId"
				+ ",  o.roleName as roleName "
				+ ",  o.roleCode as roleCode"
				+ ",  o.description as roleDesc"
				+ " from Role o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		return queryBuilder().jpqlQuery().setJpql(jpql)
		.setParams(params(params));
	}
	
	private JQuery<?> buildBindMenuGroupQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as groupId"
				+ ",  a.groupName as groupName "
				+ ",  a.groupCode as groupCode"
				+ ",  a.description as groupDesc"
				+ ",  c.id as menuId "
				+ ",  c.name as menuName"
				+ "  from Group a "
				+ " , MenuGroup b"
				+ " , Menu  c"
				+ " where a.deleted='N' and b.deleted='N' and c.deleted='N'  "
				+ " and a.id=b.groupId and b.menuId=c.id ";
		Condition condition=null;
		if((condition=params.get("menuId"))!=null){
			jpql=jpql+" and c.id =  :menuId";
		}
		return queryBuilder().jpqlQuery().setJpql(jpql)
		.setParams(params(params));
	}
	
	
	private JQuery<?> buildUnbindMenuGroupQuery(
			ServiceContext serviceContext, Map<String, Condition> params){
		String jpql="select a.id as groupId"
				+ "  from Group a "
				+ " , MenuGroup b"
				+ " , Menu  c"
				+ " where a.deleted='N' and b.deleted='N' and c.deleted='N'  "
				+ " and a.id=b.groupId and b.menuId=c.id ";
		Condition condition=null;
		if((condition=params.get("menuId"))!=null){
			jpql=jpql+" and c.id =  :menuId";
		}
		
		jpql="select o.id as groupId"
				+ ",  o.groupName as groupName "
				+ ",  o.groupCode as groupCode"
				+ ",  o.description as groupDesc"
				+ " from Group o"
				+ " where o.deleted='N' and o.id not in ("
				+jpql
				+")";
		
		return queryBuilder().jpqlQuery().setJpql(jpql)
		.setParams(params(params));
	}

	@Override
	public List<MenuRoleRecord> getBindMenuRoles(ServiceContext serviceContext,
			String menuId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("menuId", Condition.equal(menuId));
		return buildBindMenuRoleQuery(serviceContext, params)
				.models(MenuRoleRecord.class);
	}

	@Override
	public List<MenuGroupRecord> getBindMenuGroups(
			ServiceContext serviceContext, String menuId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("menuId", Condition.equal(menuId));
		return buildBindMenuGroupQuery(serviceContext, params)
				.models(MenuGroupRecord.class);
	}

	@Override
	public List<MenuRoleRecord> getUnbindMenuRoles(
			ServiceContext serviceContext, String menuId) {
		
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("menuId", Condition.equal(menuId));
		return buildUnbindMenuRoleQuery(serviceContext, params)
				.models(MenuRoleRecord.class);
	}

	@Override
	public List<MenuGroupRecord> getUnbindMenuGroups(
			ServiceContext serviceContext, String menuId) {
		Map<String, Condition> params=new HashMap<String, Condition>();
		params.put("menuId", Condition.equal(menuId));
		return buildUnbindMenuGroupQuery(serviceContext, params)
				.models(MenuGroupRecord.class);
	}

	
	
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
