/**
 * 
 */
package j.jave.platform.basicwebcomp.login.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;
import j.jave.platform.basicwebcomp.login.model.Group;

import java.util.List;

/**
 * @author J
 *
 */
public interface GroupRepo<T> extends JIPersist<T,Group> {

	Group getGroupByGroupCode(String roleCode);
	
	/**
	 * GET ALL GROUPS ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	List<Group> getGroupByGroupNameByPage(JPageable pagination);
	
	/**
	 * GET ALL GROUPS.
	 * @return
	 */
	List<Group> getAllGroups();
}
