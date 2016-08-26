package com.youappcorp.project.menumanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.menumanager.model.MenuGroupRecord;
import com.youappcorp.project.menumanager.model.MenuRecord;
import com.youappcorp.project.menumanager.model.MenuRoleRecord;
import com.youappcorp.project.menumanager.vo.MenuCriteriaInVO;

public interface MenuManagerService {

	void saveMenu(ServiceContext serviceContext,MenuRecord menuRecord);
	
	void updateMenu(ServiceContext serviceContext,MenuRecord menuRecord);
	
	void deleteMenu(ServiceContext serviceContext,MenuRecord menuRecord);
	
	void deleteMenuById(ServiceContext serviceContext,String id);
	
	MenuRecord getMenuById(ServiceContext serviceContext,String id);
	
	List<MenuRecord> getMenusByUserId(ServiceContext serviceContext,String userId);
	
	JPage<MenuRecord> getMenus(ServiceContext serviceContext,MenuCriteriaInVO menuCriteriaInVO, JSimplePageable simplePageable);
	
	List<MenuRecord> getMenus(ServiceContext serviceContext,MenuCriteriaInVO menuCriteriaInVO);
	
	void bindMenuRole(ServiceContext serviceContext,String menuId,String roleId);
	
	void bindMenuGroup(ServiceContext serviceContext,String menuId,String groupId);
	
	void unbindMenuRole(ServiceContext serviceContext,String menuId,String roleId);
	
	void unbindMenuGroup(ServiceContext serviceContext,String menuId,String groupId);
	
	List<MenuRoleRecord> getBindMenuRoles(ServiceContext serviceContext,String menuId);
	
	List<MenuGroupRecord> getBindMenuGroups(ServiceContext serviceContext,String menuId);
	
	List<MenuRoleRecord> getUnbindMenuRoles(ServiceContext serviceContext,String menuId);
	
	List<MenuGroupRecord> getUnbindMenuGroups(ServiceContext serviceContext,String menuId);
	
	
	
}
