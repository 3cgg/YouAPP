/**
 * 
 */
package j.jave.framework.components.web.subhub.sessionuser;

import java.util.List;


/**
 * the interface is support web function, to hold user in session scope. 
 * @author J
 */
public interface SessionUser {
	
	/**
	 * get user name
	 * @return
	 */
	public String getUserName() ;

	/**
	 * set user name.
	 * @param userName
	 */
	public void setUserName(String userName) ;

	/**
	 * get password , it's encrypted.
	 * @return
	 */
	public String getPassword() ;

	/**
	 * set password, it's encrypted
	 * @param password
	 */
	public void setPassword(String password) ;
	
	/**
	 * get primary key in the database.
	 * @return
	 */
	public String getId() ;

	/**
	 * set primary key from database.
	 * @param id
	 */
	public void setId(String id) ;
	
	/**
	 * get role code ,  including all roles in the group assigned to the user.
	 * @return role codes
	 */
	public List<String> getRoleCodes() ;
	
	/**
	 * set role code.
	 * @param role
	 */
	public void setRoleCodes(List<String> roleCode) ;
	
}
