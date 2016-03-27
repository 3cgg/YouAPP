/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserTracker;

/**
 * @author J
 */
public interface UserTrackerService extends Service<UserTracker>{

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
	 * @throws JServiceException
	 */
	public void saveUserTracker(ServiceContext context, UserTracker userTracker) throws JServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param userTracker
	 * @throws JServiceException
	 */
	public void updateUserTracker(ServiceContext context, UserTracker userTracker) throws JServiceException;
	

	
}
