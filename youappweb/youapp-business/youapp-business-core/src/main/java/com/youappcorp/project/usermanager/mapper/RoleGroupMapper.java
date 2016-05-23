/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.RoleGroup;
import com.youappcorp.project.usermanager.repo.RoleGroupRepo;

/**
 * @author J
 *
 */
@Component(value="roleGroupMapper.mapper")
@JModelRepo(component="roleGroupMapper.mapper",name=RoleGroup.class)
public interface RoleGroupMapper extends JMapper<RoleGroup,String>,RoleGroupRepo<JMapper<RoleGroup,String>> {
	
	int countOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	RoleGroup getRoleGroupOnRoleIdAndGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	List<RoleGroup> getRoleGroupsOnRoleIdOrGroupId(@Param(value="roleId")String roleId,@Param(value="groupId")String groupId);
	
	
}
