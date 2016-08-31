package com.youappcorp.project.menumanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;

import java.util.List;

import com.youappcorp.project.menumanager.model.MenuGroupRecord;
import com.youappcorp.project.menumanager.model.MenuRecord;
import com.youappcorp.project.menumanager.model.MenuRoleRecord;
import com.youappcorp.project.menumanager.vo.MenuCriteriaInVO;

public interface MenuManagerService {

	void saveMenu(MenuRecord menuRecord);
	
	void updateMenu(MenuRecord menuRecord);
	
	void deleteMenu(MenuRecord menuRecord);
	
	void deleteMenuById(String id);
	
	MenuRecord getMenuById(String id);
	
	List<MenuRecord> getMenusByUserId(String userId);
	
	JPage<MenuRecord> getMenus(MenuCriteriaInVO menuCriteriaInVO, JSimplePageable simplePageable);
	
	List<MenuRecord> getMenus(MenuCriteriaInVO menuCriteriaInVO);
	
	void bindMenuRole(String menuId,String roleId);
	
	void bindMenuGroup(String menuId,String groupId);
	
	void unbindMenuRole(String menuId,String roleId);
	
	void unbindMenuGroup(String menuId,String groupId);
	
	List<MenuRoleRecord> getBindMenuRoles(String menuId);
	
	List<MenuGroupRecord> getBindMenuGroups(String menuId);
	
	List<MenuRoleRecord> getUnbindMenuRoles(String menuId);
	
	List<MenuGroupRecord> getUnbindMenuGroups(String menuId);
	
	
	
}
