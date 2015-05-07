/**
 * 
 */
package j.jave.framework.components.login.subhub;

import j.jave.framework.components.core.service.ServiceContext;
import j.jave.framework.components.login.model.User;
import j.jave.framework.servicehub.JService;
import j.jave.framework.servicehub.exception.JServiceException;

/**
 * @author Administrator
 *
 */
public interface LoginAccessService  extends JService {
	
	/**
	 * if its valid user, return an unique string. 
	 * else return empty. 
	 * @param name
	 * @param password
	 * @return
	 * @throws JServiceException
	 */
	public String validate(String name,String password) throws JServiceException;
	
	/**
	 * return true if need login , else return false. 
	 * @param url
	 * @return
	 * @throws JServiceException
	 */
	public boolean  isNeedLoginRole(String url) throws JServiceException;
	
	
	/**
	 * register a user from views. its a component that wraps the logic related. 
	 * @param context
	 * @param user
	 * @throws JServiceException
	 */
	public void register(ServiceContext context,User user) throws JServiceException;
	
	
	/**
	 * if its valid user, return an unique string.  additional do some logic. 
	 * may throws {@link JServiceException} i.e.  用户不存在
	 * @param name
	 * @param password
	 * @return
	 * @throws JServiceException
	 */
	public String login(String name,String password) throws JServiceException;
	
	
	/**
	 * check whether the resource is authorized. 
	 * @param resource
	 * @param name
	 * @return
	 */
	public boolean authorizeOnUserName(String resource,String name);
	
	
	/**
	 * check whether the resource is authorized. 
	 * @param resource
	 * @param userId
	 * @return
	 */
	public boolean authorizeOnUserId(String resource,String userId);
	
}
