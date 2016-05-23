/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.User;

/**
 * @author J
 */
public interface UserRepo<T> extends JIPersist<T,User,String> {

	public User getUserByNameAndPassword(String userName,String password);
	
	public User getUserByName(String userName);
	
	public List<User> getUsersByPage(JPageable pagination) ;
	
	public List<User> getUsers();
	
}
