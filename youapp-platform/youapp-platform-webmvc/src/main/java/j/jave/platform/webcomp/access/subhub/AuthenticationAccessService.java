/**
 * 
 */
package j.jave.platform.webcomp.access.subhub;

import j.jave.platform.webcomp.core.service.SessionUserImpl;
import me.bunny.kernel.eventdriven.exception.JServiceException;
import me.bunny.kernel.jave.service.JService;

/**
 * the interface contains all ability of controlling if the end-user can access the system or not. 
 * any permit or deny function focus on the system global scope need put in this class. 
 * @author J
 */
public interface AuthenticationAccessService  extends JService {
	
	/**
	 * if its valid user, return  session user with an unique string. 
	 * else return null. 
	 * @param name
	 * @param password
	 * @return
	 * @throws JServiceException
	 */
	public SessionUserImpl validate(String name,String password) throws JServiceException;
	
	/**
	 * return true if need authorize , else return false,
	 * always true if the argument is null or empty .
	 * @param url
	 * @return
	 * @throws JServiceException
	 */
	public boolean  isNeedAuthorize(String url) throws JServiceException;
	
	/**
	 * if the user is valid, return session user with an unique string,
	 * otherwise return null (i.e.  用户不存在). 
	 * @param name
	 * @param password
	 * @return
	 * @throws JServiceException
	 */
	public SessionUserImpl login(String name,String password) throws JServiceException;
	
	
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
	
	
	/**
	 * check whether the resource is valid. i.e. the resource can be mapping to some action extends AbstractAction
	 * @param resource
	 * @return true if the resource can map, otherwise false.
	 */
	public boolean isValidResource(String resource);
	
	
}
