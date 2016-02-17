/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.RoleGroup;

import java.util.List;

/**
 * @author J
 *
 */
public interface RoleGroupRepo<T> extends JIPersist<T,RoleGroup> {
	
	int countOnRoleIdAndGroupId(String roleId,String groupId);
	
	RoleGroup getRoleGroupOnRoleIdAndGroupId(String roleId,String groupId);
	
	List<RoleGroup> getRoleGroupsOnRoleIdOrGroupId(String roleId,String groupId);
	
	
}
