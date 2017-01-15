package com.youappcorp.project.usermanager.service;

import j.jave.platform.webcomp.core.service.InternalServiceSupport;
import me.bunny.kernel.jave.persist.JIPersist;

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
	public void saveUserTracker( UserTracker userTracker)
			throws BusinessException {
		try{
			userTracker.setLoginTime(new Timestamp(new Date().getTime()));
			saveOnly( userTracker);
			
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
		
	}

	@Override
	public void updateUserTracker(
			UserTracker userTracker) throws BusinessException {
		try{
			updateOnly( userTracker);
		}catch(Exception e){
			BusinessExceptionUtil.throwException(e);
		}
		
	}
	
	@Override
	public JIPersist<?, UserTracker, String> getRepo() {
		return userTrackerMapper;
	}

}
