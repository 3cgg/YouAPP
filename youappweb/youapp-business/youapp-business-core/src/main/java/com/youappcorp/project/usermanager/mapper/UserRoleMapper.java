/**
 * 
 */
package com.youappcorp.project.usermanager.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import com.youappcorp.project.usermanager.model.UserRole;
import com.youappcorp.project.usermanager.repo.UserRoleRepo;

/**
 * @author J
 *
 */
@Component(value="userRoleMapper.mapper")
@JModelRepo(component="userRoleMapper.mapper",name=UserRole.class)
public interface UserRoleMapper extends JMapper<UserRole,String>,UserRoleRepo<JMapper<UserRole,String>> {

	List<UserRole> getUserRolesByUserId(@Param(value="userId")String userId);
	
	int countOnUserIdAndRoleId(@Param(value="userId")String userId,@Param(value="roleId")String roleId);
	
	UserRole getUserRoleOnUserIdAndRoleId(@Param(value="userId")String userId,@Param(value="roleId")String roleId);
}
