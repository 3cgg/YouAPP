/**
 * 
 */
package com.youappcorp.project.usermanager.service;

import j.jave.kernal.eventdriven.exception.JServiceException;
import j.jave.kernal.jave.model.JPage;
import j.jave.kernal.jave.model.JPageable;
import j.jave.platform.basicwebcomp.core.service.Service;
import j.jave.platform.basicwebcomp.core.service.ServiceContext;

import java.util.List;

import com.youappcorp.project.usermanager.model.User;

/**
 * @author J
 */
public interface UserService extends Service<User> {

	/**
	 * get user by name & password 
	 * @param userName
	 * @param password
	 * @return
	 */
	public User getUserByNameAndPassword(String userName,String password);
	
	/**
	 * 
	 * @param context 
	 * @param user
	 * @throws JServiceException
	 */
	public void saveUser(ServiceContext context, User user) throws JServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void updateUser(ServiceContext context, User user) throws JServiceException;
	
	/**
	 * get user by name 
	 * @param context
	 * @param userName
	 * @return
	 */
	public User getUserByName(ServiceContext context, String userName);

	/**
	 * search user 
	 * @param context
	 * @param pagination
	 * @return
	 */
	public JPage<User> getUsersByPage(ServiceContext context, JPageable pagination) ;
	
	/**
	 * 
	 * @param context
	 * @param id
	 * @return
	 */
	public User getUserById(ServiceContext context, String id);
	
	/**
	 * all users (not deleted) .
	 * @return
	 */
	public List<User> getUsers();
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void register(ServiceContext context,User user) throws JServiceException;
	
}