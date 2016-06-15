/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.platform.webcomp.core.service.Service;
import j.jave.platform.webcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.UserTracker;

/**
 * @author J
 */
public interface UserTrackerService extends Service<UserTracker, String>{

	/**
	 * get all trackers what time login, which client login. 
	 * @param userName
	 * @return
	 */
	public List<UserTracker> getUserTrackerByName(String userName);
	
	/**
	 * 
	 * @param context 
	 * @param userTracker
	 * @throws BusinessException
	 */
	public void saveUserTracker(ServiceContext context, UserTracker userTracker) throws BusinessException;
	
	
	/**
	 * 
	 * @param context
	 * @param userTracker
	 * @throws BusinessException
	 */
	public void updateUserTracker(ServiceContext context, UserTracker userTracker) throws BusinessException;
	

	
}
