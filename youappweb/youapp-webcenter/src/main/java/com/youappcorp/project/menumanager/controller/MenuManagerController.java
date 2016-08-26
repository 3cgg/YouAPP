package com.youappcorp.project.menumanager.controller;

import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageImpl;
import j.jave.kernal.jave.model.JSimplePageable;
import j.jave.kernal.jave.support.treeview.JHierarchyTreeView;
import j.jave.kernal.jave.support.treeview.JTree;
import j.jave.kernal.jave.support.treeview.JTree.Action;
import j.jave.kernal.jave.utils.JObjectUtils;
import j.jave.platform.webcomp.core.service.ServiceContext;
import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.youappcorp.project.menumanager.model.MenuGroupRecord;
import com.youappcorp.project.menumanager.model.MenuRecord;
import com.youappcorp.project.menumanager.model.MenuRoleRecord;
import com.youappcorp.project.menumanager.service.MenuManagerService;
import com.youappcorp.project.menumanager.vo.MenuCriteriaInVO;
import com.youappcorp.project.menumanager.vo.MenuGroupRecordVO;
import com.youappcorp.project.menumanager.vo.MenuRecordVO;
import com.youappcorp.project.menumanager.vo.MenuRoleRecordVO;

/**
 * @author JIAZJ
 */
@Controller
@RequestMapping("/menumanager")
public class MenuManagerController extends SimpleControllerSupport {

	@Autowired
	private MenuManagerService menuManagerService;
	
	@ResponseBody
	@RequestMapping("/saveMenu")
	public ResponseModel saveMenu(ServiceContext serviceContext, MenuRecordVO menuRecordVO) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.saveMenu(serviceContext, menuRecordVO);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/updateMenu")
	public ResponseModel updateMenu(ServiceContext serviceContext, MenuRecordVO menuRecordVO) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.updateMenu(serviceContext, menuRecordVO);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/getMenuById")
	public ResponseModel getMenuById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the Menu or nothing.
		MenuRecord menuRecord=menuManagerService.getMenuById(serviceContext, id);
		return ResponseModel.newSuccess(toMenuRecordVO(menuRecord));
	}
	
	private MenuRecordVO toMenuRecordVO(MenuRecord menuRecord){
		return JObjectUtils.simpleCopy(menuRecord, MenuRecordVO.class);
	}
	
	private List<MenuRecordVO> toMenuRecordVOs(List<MenuRecord> menuRecords){
		List<MenuRecordVO> menuRecordVOs=new ArrayList<MenuRecordVO>();
		for(MenuRecord menuRecord:menuRecords){
			menuRecordVOs.add(toMenuRecordVO(menuRecord));
		}
		return menuRecordVOs;
	}
	
	@ResponseBody
	@RequestMapping("/deleteMenuById")
	public ResponseModel deleteMenuById(ServiceContext serviceContext, String id) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.deleteMenuById(serviceContext, id);
		return ResponseModel.newSuccess(true);
	}
	
	
	@ResponseBody
	@RequestMapping("/getMenusByPage")
	public ResponseModel getMenusByPage(ServiceContext serviceContext, MenuCriteriaInVO menuCriteriaInVO,JSimplePageable simplePageable ) throws Exception{
		// do something validation on the Menu or nothing.
		JPage<MenuRecord> page=menuManagerService.getMenus(serviceContext, menuCriteriaInVO,simplePageable);
		page.setContent(toMenuRecordVOs(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getMenusByUserId")
	public ResponseModel getMenusByUserId(ServiceContext serviceContext, String userId ) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenusByUserId(serviceContext, userId);
		return ResponseModel.newSuccess(toMenuRecordVOs(menuRecords));
	}
	
	@ResponseBody
	@RequestMapping("/getMenus")
	public ResponseModel getMenus(ServiceContext serviceContext, MenuCriteriaInVO menuCriteriaInVO) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenus(serviceContext, menuCriteriaInVO);
		return ResponseModel.newSuccess(toMenuRecordVOs(menuRecords));
	}
	
	@ResponseBody
	@RequestMapping("/getMenusTree")
	public ResponseModel getMenusTree(ServiceContext serviceContext, MenuCriteriaInVO menuCriteriaInVO) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenus(serviceContext, menuCriteriaInVO);
		JTree tree=new JTree(toMenuRecordVOs(menuRecords),Action.DROP).get();
		JHierarchyTreeView hierarchyTreeView=new JHierarchyTreeView(tree);
		return ResponseModel.newSuccess(hierarchyTreeView.models());
	}
	
	@ResponseBody
	@RequestMapping("/getBindMenuRoles")
	public ResponseModel  getBindMenuRoles(ServiceContext serviceContext,String menuId){
		List<MenuRoleRecord> menuRoleRecrods=menuManagerService.getBindMenuRoles(serviceContext, menuId);
		JPage<?> page=JPageImpl.wrap(menuRoleRecrods);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbindMenuRoles")
	public ResponseModel  getUnbindMenuRoles(ServiceContext serviceContext,String menuId){
		List<MenuRoleRecord> menuRoleRecrods=menuManagerService.getUnbindMenuRoles(serviceContext, menuId);
		JPage<?> page=JPageImpl.wrap(menuRoleRecrods);
		return ResponseModel.newSuccess(page);
	}
	
	
	@ResponseBody
	@RequestMapping("/getBindMenuGroups")
	public ResponseModel  getBindMenuGroups(ServiceContext serviceContext,String menuId){
		List<MenuGroupRecord> menuGroupRecords=menuManagerService.getBindMenuGroups(serviceContext, menuId);
		JPage<?> page=JPageImpl.wrap(menuGroupRecords);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbindMenuGroups")
	public ResponseModel  getUnbindMenuGroups(ServiceContext serviceContext,String menuId){
		List<MenuGroupRecord> menuGroupRecords=menuManagerService.getUnbindMenuGroups(serviceContext, menuId);
		JPage<?> page=JPageImpl.wrap(menuGroupRecords);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/bindMenuGroup")
	public ResponseModel  bindMenuGroup(ServiceContext serviceContext,MenuGroupRecordVO menuGroupRecord){
		menuManagerService.bindMenuGroup(serviceContext, menuGroupRecord.getMenuId(),
				menuGroupRecord.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/bindMenuRole")
	public ResponseModel  bindMenuRole(ServiceContext serviceContext,MenuRoleRecordVO menuRoleRecord){
		menuManagerService.bindMenuRole(serviceContext, menuRoleRecord.getMenuId(),
				menuRoleRecord.getRoleId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbindMenuGroup")
	public ResponseModel  unbindMenuGroup(ServiceContext serviceContext,MenuGroupRecordVO menuGroupRecord){
		menuManagerService.unbindMenuGroup(serviceContext, menuGroupRecord.getMenuId(),
				menuGroupRecord.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbindMenuRole")
	public ResponseModel  unbindMenuRole(ServiceContext serviceContext,MenuRoleRecordVO menuRoleRecord){
		menuManagerService.unbindMenuRole(serviceContext, menuRoleRecord.getMenuId(),
				menuRoleRecord.getRoleId());
		return ResponseModel.newSuccess();
	}
}
