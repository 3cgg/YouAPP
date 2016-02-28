/**
 * 
 */
package j.jave.platform.basicwebcomp.login.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.login.model.RoleGroup;
import j.jave.platform.basicwebcomp.login.repo.RoleGroupRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="roleGroupMapper.mapper")
@JModelRepo(component="roleGroupMapper.mapper",name=RoleGroup.class)
public interface RoleGroupMapper extends JMapper<RoleGroup>,RoleGroupRepo<JMapper<RoleGroup>> {
	
	int countOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	RoleGroup getRoleGroupOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	List<RoleGroup> getRoleGroupsOnRoleIdOrGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	
}
