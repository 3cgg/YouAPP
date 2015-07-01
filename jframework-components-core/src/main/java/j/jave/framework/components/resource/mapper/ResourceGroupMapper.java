/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import j.jave.framework.commons.model.support.JModelMapper;
import j.jave.framework.components.resource.model.ResourceGroup;
import j.jave.framework.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceGroupMapper")
@JModelMapper(component="ResourceGroupMapper",name=ResourceGroup.class)
public interface ResourceGroupMapper extends JMapper<ResourceGroup> {

	List<ResourceGroup> getResourceGroupsByResourceId(@Param(value="resourceId")String resourceId);
	
	int countOnResourceIdAndGroupId(@Param(value="resourceId")String resourceId,@Param(value="groupId")String groupId);
	
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(@Param(value="resourceId")String resourceId,@Param(value="groupId")String groupId);
	
	
}
