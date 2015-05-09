/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.components.resource.model.ResourceRole;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 */
@Component(value="ResourceRoleMapper")
@JModelMapper(component="ResourceRoleMapper",name=ResourceRole.class)
public interface ResourceRoleMapper extends JMapper<ResourceRole> {

	List<ResourceRole> getResourceRolesByResourceId(@Param(value="resourceId")String resourceId);
	
	int countOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
	ResourceRole getResourceRoleOnResourceIdAndRoleId(@Param(value="resourceId")String resourceId,@Param(value="roleId")String roleId);
	
}
