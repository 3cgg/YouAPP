/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.login.model.UserTracker;

import java.util.List;

/**
 * @author Administrator
 *
 */
public interface UserTrackerService {

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
	 * @throws ServiceException
	 */
	public void saveUserTracker(ServiceContext context, UserTracker userTracker) throws ServiceException;
	
	
	/**
	 * 
	 * @param context
	 * @param userTracker
	 * @throws ServiceException
	 */
	public void updateUserTracker(ServiceContext context, UserTracker userTracker) throws ServiceException;
	

	
}
