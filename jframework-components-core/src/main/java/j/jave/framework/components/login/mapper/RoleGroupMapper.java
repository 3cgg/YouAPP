/**
 * 
 */
package j.jave.framework.components.login.mapper;

import java.util.List;

import j.jave.framework.components.login.model.RoleGroup;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="RoleGroupMapper")
@JModelMapper(component="RoleGroupMapper",name=RoleGroup.class)
public interface RoleGroupMapper extends JMapper<RoleGroup> {
	
	int countOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	RoleGroup getRoleGroupOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	List<RoleGroup> getRoleGroupsOnRoleIdOrGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	
}
