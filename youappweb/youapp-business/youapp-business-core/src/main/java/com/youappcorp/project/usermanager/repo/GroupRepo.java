/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import com.youappcorp.project.usermanager.model.Group;

/**
 * @author J
 *
 */
public interface GroupRepo<T> extends JIPersist<T,Group,String> {

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
