/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import java.util.List;

import com.youappcorp.project.usermanager.model.UserTracker;

import me.bunny.kernel.jave.persist.JIPersist;

/**
 * @author J
 */
public interface UserTrackerRepo<P> extends JIPersist<P,UserTracker,String> {

	
	public List<UserTracker> getUserTrackerByName(String userName);
	
	
}
