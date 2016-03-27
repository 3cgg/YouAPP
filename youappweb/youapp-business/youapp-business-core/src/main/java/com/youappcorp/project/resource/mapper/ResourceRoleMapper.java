/**
 * 
 */
package com.youappcorp.project.resource.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import com.youappcorp.project.resource.model.ResourceRole;
import com.youappcorp.project.resource.repo.ResourceRoleRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="resourceRoleMapper.mapper")
@JModelRepo(component="resourceRoleMapper.mapper",name=ResourceRole.class)
public interface ResourceRoleMapper extends JMapper<ResourceRole>,ResourceRoleRepo<JMapper<ResourceRole>> {

	List<ResourceRole> getResourceRolesByResourceId(@Param(value="resourceId")String resourceId);
	
	int countOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
	ResourceRole getResourceRoleOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
}
