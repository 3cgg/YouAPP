/**
 * 
 */
package j.jave.framework.components.login.service;

import j.jave.framework.components.core.context.ServiceContext;
import j.jave.framework.components.core.exception.ServiceException;
import j.jave.framework.components.core.hub.Service;
import j.jave.framework.components.login.model.User;

/**
 * @author Administrator
 *
 */
public interface LoginAccessService  extends Service {
	
	/**
	 * if its valid user, return an unique string. 
	 * else return empty. 
	 * @param name
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public String validate(String name,String password) throws ServiceException;
	
	/**
	 * return true if need login , else return false. 
	 * @param url
	 * @return
	 * @throws ServiceException
	 */
	public boolean  isNeedLoginRole(String url) throws ServiceException;
	
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * @param context
	 * @param user
	 * @throws ServiceException
	 */
	public void register(ServiceContext context,User user) throws ServiceException;
	
	
	/**
	 * if its valid user, return an unique string.  additional do some logic. 
	 * may throws {@link ServiceException} i.e.  用户不存在
	 * @param name
	 * @param password
	 * @return
	 * @throws ServiceException
	 */
	public String login(String name,String password) throws ServiceException;
}
