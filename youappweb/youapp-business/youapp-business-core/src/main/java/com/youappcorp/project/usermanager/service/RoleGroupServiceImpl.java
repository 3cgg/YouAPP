package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.RoleGroup;
import com.youappcorp.project.usermanager.repo.RoleGroupRepo;

@Service(value="roleGroupServiceImpl.transation.jpa")
public class RoleGroupServiceImpl extends InternalServiceSupport<RoleGroup> implements RoleGroupService {

	@Autowired
	private RoleGroupRepo<?>  roleGroupMapper;
	
	@Override
	public JIPersist<?, RoleGroup> getRepo() {
		return roleGroupMapper;
	}
	
	@Override
	public void bingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) throws BusinessException {
		
		try{
			if(isBing(serviceContext, roleId, groupId)){
				throw new BusinessException("the role had already belong to the group.");
			}
			
			RoleGroup userGroup=new RoleGroup();
			userGroup.setRoleId(roleId);
			userGroup.setGroupId(groupId);
			userGroup.setId(JUniqueUtils.unique());
			saveOnly(serviceContext, userGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
	}
	
	@Override
	public void unbingRoleGroup(ServiceContext serviceContext,String roleId,String groupId) 
			throws BusinessException {
		
		try{

			RoleGroup userGroup=getRoleGroupOnRoleIdAndGroupId(serviceContext, roleId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			delete(serviceContext, userGroup.getId());
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}

	@Override
	public RoleGroup getRoleGroupOnRoleIdAndGroupId(
			ServiceContext serviceContext, String roleId, String groupId) {
		return roleGroupMapper.getRoleGroupOnRoleIdAndGroupId(roleId, groupId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext,String roleId,String groupId) {
		int count=roleGroupMapper.countOnRoleIdAndGroupId(roleId, groupId);
		return count>0;
	}

	@Override
	public List<RoleGroup> getRoleGroupsByRoleId(ServiceContext serviceContext,
			String roleId) {
		return roleGroupMapper.getRoleGroupsOnRoleIdOrGroupId(roleId, null);
	}

	@Override
	public List<RoleGroup> getRoleGroupsByGroupId(
			ServiceContext serviceContext, String groupId) {
		return roleGroupMapper.getRoleGroupsOnRoleIdOrGroupId(null, groupId);
	}
	
}
