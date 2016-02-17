/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.resource.model.ResourceRole;
import j.jave.platform.basicwebcomp.resource.repo.ResourceRoleRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="ResourceRoleMapper")
@JModelMapper(component="ResourceRoleMapper",name=ResourceRole.class)
public interface ResourceRoleMapper extends JMapper<ResourceRole>,ResourceRoleRepo<JMapper<ResourceRole>> {

	List<ResourceRole> getResourceRolesByResourceId(@Param(value="resourceId")String resourceId);
	
	int countOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
	ResourceRole getResourceRoleOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
}
