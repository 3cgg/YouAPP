package com.youappcorp.project.menumanager.controller;

import j.jave.platform.webcomp.web.model.ResponseModel;
import j.jave.platform.webcomp.web.youappmvc.controller.SimpleControllerSupport;
import me.bunny.kernel._c.model.JPage;
import me.bunny.kernel._c.model.JPageImpl;
import me.bunny.kernel._c.model.JSimplePageable;
import me.bunny.kernel._c.support.treeview.JHierarchyTreeView;
import me.bunny.kernel._c.support.treeview.JTree;
import me.bunny.kernel._c.support.treeview.JTree.Action;
import me.bunny.kernel._c.utils.JObjectUtils;

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
	public ResponseModel saveMenu( MenuRecordVO menuRecordVO) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.saveMenu( menuRecordVO);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/updateMenu")
	public ResponseModel updateMenu( MenuRecordVO menuRecordVO) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.updateMenu( menuRecordVO);
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/getMenuById")
	public ResponseModel getMenuById( String id) throws Exception{
		// do something validation on the Menu or nothing.
		MenuRecord menuRecord=menuManagerService.getMenuById( id);
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
	public ResponseModel deleteMenuById( String id) throws Exception{
		// do something validation on the Menu or nothing.
		menuManagerService.deleteMenuById( id);
		return ResponseModel.newSuccess(true);
	}
	
	
	@ResponseBody
	@RequestMapping("/getMenusByPage")
	public ResponseModel getMenusByPage( MenuCriteriaInVO menuCriteriaInVO,JSimplePageable simplePageable ) throws Exception{
		// do something validation on the Menu or nothing.
		JPage<MenuRecord> page=menuManagerService.getMenus( menuCriteriaInVO,simplePageable);
		page.setContent(toMenuRecordVOs(page.getContent()));
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getMenusByUserId")
	public ResponseModel getMenusByUserId( String userId ) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenusByUserId( userId);
		return ResponseModel.newSuccess(toMenuRecordVOs(menuRecords));
	}
	
	@ResponseBody
	@RequestMapping("/getMenus")
	public ResponseModel getMenus( MenuCriteriaInVO menuCriteriaInVO) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenus( menuCriteriaInVO);
		return ResponseModel.newSuccess(toMenuRecordVOs(menuRecords));
	}
	
	@ResponseBody
	@RequestMapping("/getMenusTree")
	public ResponseModel getMenusTree( MenuCriteriaInVO menuCriteriaInVO) throws Exception{
		// do something validation on the Menu or nothing.
		List<MenuRecord> menuRecords=menuManagerService.getMenus( menuCriteriaInVO);
		JTree tree=new JTree(toMenuRecordVOs(menuRecords),Action.DROP).get();
		JHierarchyTreeView hierarchyTreeView=new JHierarchyTreeView(tree);
		return ResponseModel.newSuccess(hierarchyTreeView.models());
	}
	
	@ResponseBody
	@RequestMapping("/getBindMenuRoles")
	public ResponseModel  getBindMenuRoles(String menuId){
		List<MenuRoleRecord> menuRoleRecrods=menuManagerService.getBindMenuRoles( menuId);
		JPage<?> page=JPageImpl.wrap(menuRoleRecrods);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbindMenuRoles")
	public ResponseModel  getUnbindMenuRoles(String menuId){
		List<MenuRoleRecord> menuRoleRecrods=menuManagerService.getUnbindMenuRoles( menuId);
		JPage<?> page=JPageImpl.wrap(menuRoleRecrods);
		return ResponseModel.newSuccess(page);
	}
	
	
	@ResponseBody
	@RequestMapping("/getBindMenuGroups")
	public ResponseModel  getBindMenuGroups(String menuId){
		List<MenuGroupRecord> menuGroupRecords=menuManagerService.getBindMenuGroups( menuId);
		JPage<?> page=JPageImpl.wrap(menuGroupRecords);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/getUnbindMenuGroups")
	public ResponseModel  getUnbindMenuGroups(String menuId){
		List<MenuGroupRecord> menuGroupRecords=menuManagerService.getUnbindMenuGroups( menuId);
		JPage<?> page=JPageImpl.wrap(menuGroupRecords);
		return ResponseModel.newSuccess(page);
	}
	
	@ResponseBody
	@RequestMapping("/bindMenuGroup")
	public ResponseModel  bindMenuGroup(MenuGroupRecordVO menuGroupRecord){
		menuManagerService.bindMenuGroup( menuGroupRecord.getMenuId(),
				menuGroupRecord.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/bindMenuRole")
	public ResponseModel  bindMenuRole(MenuRoleRecordVO menuRoleRecord){
		menuManagerService.bindMenuRole( menuRoleRecord.getMenuId(),
				menuRoleRecord.getRoleId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbindMenuGroup")
	public ResponseModel  unbindMenuGroup(MenuGroupRecordVO menuGroupRecord){
		menuManagerService.unbindMenuGroup( menuGroupRecord.getMenuId(),
				menuGroupRecord.getGroupId());
		return ResponseModel.newSuccess();
	}
	
	@ResponseBody
	@RequestMapping("/unbindMenuRole")
	public ResponseModel  unbindMenuRole(MenuRoleRecordVO menuRoleRecord){
		menuManagerService.unbindMenuRole( menuRoleRecord.getMenuId(),
				menuRoleRecord.getRoleId());
		return ResponseModel.newSuccess();
	}
}
