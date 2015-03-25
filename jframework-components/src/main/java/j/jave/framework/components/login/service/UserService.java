/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.login.model.User;

/**
 * @author Administrator
 *
 */
public interface UserService {

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
	 * @throws ServiceException
	 */
	public void saveUser(ServiceContext context, User user) throws ServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void updateUser(ServiceContext context, User user) throws ServiceException;
	
	/**
	 * get user by name 
	 * @param context
	 * @param userName
	 * @return
	 */
	public User getUserByName(ServiceContext context, String userName);
	
}
