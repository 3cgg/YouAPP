/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.UserGroup;
import com.youappcorp.project.usermanager.repo.UserGroupRepo;

/**
 * @author J
 *
 */
@Component(value="userGroupMapper.mapper")
@JModelRepo(component="userGroupMapper.mapper",name=UserGroup.class)
public interface UserGroupMapper extends JMapper<UserGroup>,UserGroupRepo<JMapper<UserGroup>> {
	
	List<UserGroup> getUserGroupsByUserId(@Param(value="userId")String userId);
	
	int countOnUserIdAndGroupId(@Param(value="userId")String userId,@Param(value="groupId")String groupId);
	
	UserGroup getUserGroupOnUserIdAndGroupId(@Param(value="userId")String userId,@Param(value="groupId")String groupId);
	
}
