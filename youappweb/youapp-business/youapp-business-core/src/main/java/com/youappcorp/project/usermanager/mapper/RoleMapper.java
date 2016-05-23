/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.Role;
import com.youappcorp.project.usermanager.repo.RoleRepo;

/**
 * @author J
 *
 */
@Component(value="roleMapper.mapper")
@JModelRepo(component="roleMapper.mapper",name=Role.class)
public interface RoleMapper extends JMapper<Role,String>,RoleRepo<JMapper<Role,String>> {

	Role getRoleByRoleCode(@Param(value="roleCode")String roleCode);
	
//	/**
//	 * GET ALL ROLES ACCORDING TO 'ROLE NAME'
//	 * @param pagination
//	 * @return
//	 */
//	List<Role> getRoleByRoleNameByPage(JPageable pagination);
	
	/**
	 * GET ALL ROLES.
	 * @return
	 */
	List<Role> getAllRoles();
	
	
	int count_for_getRoleByRoleNameByPage(Pageable pageable,
			@Param(value="param") JPageable pageParameter);
	
	Page<Role> getRoleByRoleNameByPage(Pageable pageable,
			@Param(value="param") JPageable pageParameter);
	
	
	
}
