/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.JPageable;
import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.login.model.Role;
import j.jave.platform.basicwebcomp.login.repo.RoleRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="roleMapper.mapper")
@JModelRepo(component="roleMapper.mapper",name=Role.class)
public interface RoleMapper extends JMapper<Role>,RoleRepo<JMapper<Role>> {

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
	
	
	int countFor_getRoleByRoleNameByPage(Pageable pageable,
			@Param(value="param") JPageable pageParameter);
	
	Page<Role> getRoleByRoleNameByPage(Pageable pageable,
			@Param(value="param") JPageable pageParameter);
	
	
	
}
