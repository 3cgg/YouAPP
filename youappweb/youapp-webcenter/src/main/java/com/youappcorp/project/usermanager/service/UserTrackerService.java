/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.platform.webcomp.core.service.InternalService;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.UserTracker;

/**
 * @author J
 */
public interface UserTrackerService extends InternalService<UserTracker, String>{

	/**
	 * get all trackers what time login, which client login. 
	 * @param userName
	 * @return
	 */
	public List<UserTracker> getUserTrackerByName(String userName);
	
	/**
	 * 
	 *  
	 * @param userTracker
	 * @throws BusinessException
	 */
	public void saveUserTracker( UserTracker userTracker) throws BusinessException;
	
	
	/**
	 * 
	 * 
	 * @param userTracker
	 * @throws BusinessException
	 */
	public void updateUserTracker( UserTracker userTracker) throws BusinessException;
	

	
}
