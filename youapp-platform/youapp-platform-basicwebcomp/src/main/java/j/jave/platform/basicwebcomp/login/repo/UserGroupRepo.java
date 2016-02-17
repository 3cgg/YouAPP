/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.UserGroup;

import java.util.List;

/**
 * @author J
 *
 */
public interface UserGroupRepo<T> extends JIPersist<T,UserGroup> {
	
	List<UserGroup> getUserGroupsByUserId(String userId);
	
	int countOnUserIdAndGroupId(String userId,String groupId);
	
	UserGroup getUserGroupOnUserIdAndGroupId(String userId,String groupId);
	
}
