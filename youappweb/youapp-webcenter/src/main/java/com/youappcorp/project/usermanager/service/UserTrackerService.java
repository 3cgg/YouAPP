/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import java.util.List;

import com.youappcorp.project.BusinessException;
import com.youappcorp.project.usermanager.model.UserTracker;

import me.bunny.app._c._web.core.service.InternalService;

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
