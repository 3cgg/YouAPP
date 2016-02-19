/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.JPagination;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.basicwebcomp.login.repo.RoleRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="RoleMapper")
@JModelRepo(component="RoleMapper",name=Role.class)
public interface RoleMapper extends JMapper<Role>,RoleRepo<JMapper<Role>> {

	Role getRoleByRoleCode(@Param(value="roleCode")String roleCode);
	
	/**
	 * GET ALL ROLES ACCORDING TO 'ROLE NAME'
	 * @param pagination
	 * @return
	 */
	List<Role> getRoleByRoleNameByPage(JPagination pagination);
	
	/**
	 * GET ALL ROLES.
	 * @return
	 */
	List<Role> getAllRoles();
	
	
	
	
	
	
}
