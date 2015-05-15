/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.service.Service;
import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.components.login.model.UserExtend;
import j.jave.framework.servicehub.exception.JServiceException;

/**
 * @author J
 */
public interface UserExtendService extends Service<UserExtend> {
	
	
	/**
	 * save user extension , that is always called by UserService. including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 * @param context 
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void saveUserExtend(ServiceContext context, UserExtend userExtend) throws JServiceException;
	
	
	/**
	 * update user extension,including some validations:
	 * <p>1. user id is not null
	 * <p>2. user name is not null
	 * <p>3. user nature name is unique in the system
	 * @param context
	 * @param userExtend
	 * @throws JServiceException
	 */
	public void updateUserExtend(ServiceContext context, UserExtend userExtend) throws JServiceException;
	
	/**
	 * get user extension by user id that is the primary key in the user model.
	 * @param context
	 * @param userId
	 * @return
	 * @see User
	 */
	public UserExtend getUserExtendByUserId(ServiceContext context, String userId);
	
	/**
	 * get user extension by nature name . the nature name is also unique in the system.
	 * @param context
	 * @param natureName  unique 
	 * @return
	 */
	public UserExtend getUserExtendByNatureName(ServiceContext context, String natureName);
	
}
