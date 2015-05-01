/**
 * 
 */
package j.jave.framework.components.login.mapper;

import java.util.List;

import j.jave.framework.components.login.model.Role;
import j.jave.framework.model.JPagination;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="RoleMapper")
@JModelMapper(component="RoleMapper",name=Role.class)
public interface RoleMapper extends JMapper<Role> {

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
