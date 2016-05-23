/**
 * 
 */
package com.youappcorp.project.usermanager.repo;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.persist.JIPersist;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.youappcorp.project.usermanager.model.Role;

/**
 * @author J
 *
 */
public interface RoleRepo<T> extends JIPersist<T,Role,String> {

	Role getRoleByRoleCode(String roleCode);
	
	/**
	 * GET ALL ROLES ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	Page<Role> getRoleByRoleNameByPage(Pageable pageable,JPageable pageParameter);
	
	/**
	 * GET ALL ROLES.
	 * @return
	 */
	List<Role> getAllRoles();
	
	
	
	
	
	
}
