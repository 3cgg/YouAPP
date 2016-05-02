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
import com.youappcorp.project.usermanager.model.UserGroup;
import com.youappcorp.project.usermanager.repo.UserGroupRepo;

@Service(value="userGroupServiceImpl.transation.jpa")
public class UserGroupServiceImpl extends InternalServiceSupport<UserGroup> implements UserGroupService {

	@Autowired
	private UserGroupRepo<?> userGroupMapper;
	
	@Override
	public JIPersist<?, UserGroup> getRepo() {
		return userGroupMapper;
	}
	@Override
	public List<UserGroup> getUserGroupsByUserId(ServiceContext serviceContext,
			String userId) {
		return userGroupMapper.getUserGroupsByUserId(userId);
	}
	
	@Override
	public void bingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		
		try{

			if(isBing(serviceContext, userId, groupId)){
				throw new BusinessException("the user had already belong to the group.");
			}
			
			UserGroup userGroup=new UserGroup();
			userGroup.setUserId(userId);
			userGroup.setGroupId(groupId);
			userGroup.setId(JUniqueUtils.unique());
			saveOnly(serviceContext, userGroup);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public void unbingUserGroup(ServiceContext serviceContext, String userId,
			String groupId) throws BusinessException {
		try{
			UserGroup userGroup=getUserGroupOnUserIdAndGroupId(serviceContext, userId, groupId);
			if(userGroup==null){
				throw new BusinessException("the user doesnot belong to the group.");
			}
			delete(serviceContext, userGroup.getId());
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public UserGroup getUserGroupOnUserIdAndGroupId(ServiceContext serviceContext,
			String userId, String groupId) {
		return userGroupMapper.getUserGroupOnUserIdAndGroupId(userId, groupId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String userId,
			String groupId) {
		int count=userGroupMapper.countOnUserIdAndGroupId(userId, groupId);
		return count>0;
	}
	
	
}
