/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.persist.JIPersist;

import com.youappcorp.project.usermanager.model.UserExtend;

/**
 * @author J
 */
public interface UserExtendRepo<T> extends JIPersist<T,UserExtend ,String> {
	
	public UserExtend getUserExtendByUserId(String userId);

	public UserExtend getUserExtendByNatureName(String natureName);
	
}
