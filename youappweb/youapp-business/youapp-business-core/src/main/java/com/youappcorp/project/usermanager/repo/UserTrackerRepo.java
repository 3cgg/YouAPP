/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserTracker;

/**
 * @author J
 */
public interface UserTrackerRepo<P> extends JIPersist<P,UserTracker,String> {

	
	public List<UserTracker> getUserTrackerByName(String userName);
	
	
}
