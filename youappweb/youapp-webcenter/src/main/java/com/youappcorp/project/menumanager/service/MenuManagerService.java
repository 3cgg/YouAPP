package com.youappcorp.project.menumanager.service;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.menumanager.model.MenuRecord;
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
	
	
}
