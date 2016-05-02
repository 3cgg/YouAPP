package com.youappcorp.project.usermanager.service;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.core.service.InternalServiceSupport;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.BusinessExceptionUtil;
import com.youappcorp.project.usermanager.model.UserTracker;
import com.youappcorp.project.usermanager.repo.UserTrackerRepo;

@Service(value="userTrackerService.transation.jpa")
public class UserTrackerServiceImpl extends InternalServiceSupport<UserTracker> implements UserTrackerService {

	@Autowired
	private UserTrackerRepo<?> userTrackerMapper;
	
	@Override
	public List<UserTracker> getUserTrackerByName(String userName) {
		return userTrackerMapper.getUserTrackerByName(userName);
	}

	@Override
	public void saveUserTracker(ServiceContext context, UserTracker userTracker)
			throws BusinessException {
		try{
			userTracker.setLoginTime(new Timestamp(new Date().getTime()));
			saveOnly(context, userTracker);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}

	@Override
	public void updateUserTracker(ServiceContext context,
			UserTracker userTracker) throws BusinessException {
		try{
			updateOnly(context, userTracker);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public JIPersist<?, UserTracker> getRepo() {
		return userTrackerMapper;
	}

}
