/**
 * 
 */
package j.jave.platform.webcomp.core.service;

import java.util.List;

import me.bunny.kernel.jave.model.JModel;


/**
 * the interface is support web function, to hold user in session scope. 
 * @author J
 */
public interface SessionUser extends JModel {
	
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
	 * get primary key in the database.
	 * @return
	 */
	public String getUserId() ;

	/**
	 * set primary key from database.
	 * @param id
	 */
	public void setUserId(String id) ;
	
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
