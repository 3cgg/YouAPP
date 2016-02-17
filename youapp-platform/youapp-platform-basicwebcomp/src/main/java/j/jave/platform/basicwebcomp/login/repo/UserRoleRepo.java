/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.UserRole;

import java.util.List;

/**
 * @author J
 *
 */
public interface UserRoleRepo<T> extends JIPersist<T,UserRole> {

	List<UserRole> getUserRolesByUserId(String userId);
	
	int countOnUserIdAndRoleId(String userId,String roleId);
	
	UserRole getUserRoleOnUserIdAndRoleId(String userId,String roleId);
}
