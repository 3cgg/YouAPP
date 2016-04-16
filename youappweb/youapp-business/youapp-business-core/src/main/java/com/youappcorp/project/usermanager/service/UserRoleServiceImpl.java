package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.kernal.jave.utils.JUniqueUtils;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;
import j.jave.platform.basicwebcomp.core.service.ServiceSupport;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.usermanager.model.UserRole;
import com.youappcorp.project.usermanager.repo.UserRoleRepo;

@Service(value="userRoleServiceImpl.transation.jpa")
public class UserRoleServiceImpl extends ServiceSupport<UserRole> implements UserRoleService {

	@Autowired
	private UserRoleRepo<?> userRoleMapper;
	
	@Override
	public JIPersist<?, UserRole> getRepo() {
		return userRoleMapper;
	}
	
	@Override
	public List<UserRole> getUserRolesByUserId(ServiceContext serviceContext,
			String userId) {
		return userRoleMapper.getUserRolesByUserId(userId);
	}
	
	@Override
	public void bingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws JServiceException {
		if(isBing(serviceContext, userId, roleId)){
			throw new JServiceException("the user had already the role.");
		}
		
		UserRole userRole=new UserRole();
		userRole.setUserId(userId);
		userRole.setRoleId(roleId);
		userRole.setId(JUniqueUtils.unique());
		saveOnly(serviceContext, userRole);
	}
	
	@Override
	public void unbingUserRole(ServiceContext serviceContext, String userId,
			String roleId) throws JServiceException {
		
		UserRole userRole=getUserRoleOnUserIdAndRoleId(serviceContext, userId, roleId);
		if(userRole==null){
			throw new JServiceException("the user doesnot have the role.");
		}
		delete(serviceContext, userRole.getId());
	}
	
	@Override
	public UserRole getUserRoleOnUserIdAndRoleId(ServiceContext serviceContext,
			String userId, String roleId) {
		return userRoleMapper.getUserRoleOnUserIdAndRoleId(userId, roleId);
	}
	
	@Override
	public boolean isBing(ServiceContext serviceContext, String userId,
			String roleId) {
		int count=userRoleMapper.countOnUserIdAndRoleId(userId, roleId);
		return count>0;
	}
	
}
