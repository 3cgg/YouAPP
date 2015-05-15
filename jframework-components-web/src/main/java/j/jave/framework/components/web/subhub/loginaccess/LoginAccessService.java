/**
 * 
 */
package j.jave.framework.components.web.subhub.loginaccess;

import j.jave.framework.servicehub.JService;
import j.jave.framework.servicehub.exception.JServiceException;

/**
 * the interface contains all ability of controlling if the end-user can access the system or not. 
 * any permit or deny function focus on the system global scope need put in this class. 
 * @author J
 */
public interface LoginAccessService  extends JService ,JResourceStaticMemoryCacheIO {
	
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
	 * return true if need login , else return false,
	 * always true if the argument is null or empty .
	 * @param url
	 * @return
	 * @throws JServiceException
	 */
	public boolean  isNeedLoginRole(String url) throws JServiceException;
	
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
	
	
	/**
	 * check whether the resource is valid. i.e. the resource can be mapping to some action extends AbstractAction
	 * @param resource
	 * @return true if the resource can map, otherwise false.
	 */
	public boolean isValidResource(String resource);
	
	
}
