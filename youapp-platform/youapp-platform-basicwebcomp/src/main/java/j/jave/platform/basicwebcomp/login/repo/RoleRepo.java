/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.Role;

import java.util.List;

/**
 * @author J
 *
 */
public interface RoleRepo<T> extends JIPersist<T,Role> {

	Role getRoleByRoleCode(String roleCode);
	
	/**
	 * GET ALL ROLES ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	List<Role> getRoleByRoleNameByPage(JPageable pagination);
	
	/**
	 * GET ALL ROLES.
	 * @return
	 */
	List<Role> getAllRoles();
	
	
	
	
	
	
}
