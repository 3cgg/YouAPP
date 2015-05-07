/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.UserTracker;
import j.jave.framework.servicehub.exception.JServiceException;

import java.util.List;

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
