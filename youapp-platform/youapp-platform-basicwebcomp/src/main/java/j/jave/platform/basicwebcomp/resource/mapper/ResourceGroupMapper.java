/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.resource.model.ResourceGroup;
import j.jave.platform.basicwebcomp.resource.repo.ResourceGroupRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="resourceGroupMapper.mapper")
@JModelRepo(component="resourceGroupMapper.mapper",name=ResourceGroup.class)
public interface ResourceGroupMapper extends JMapper<ResourceGroup>,ResourceGroupRepo<JMapper<ResourceGroup>> {

	List<ResourceGroup> getResourceGroupsByResourceId(@Param(value="resourceId")String resourceId);
	
	int countOnResourceIdAndGroupId(@Param(value="resourceId")String resourceId,@Param(value="groupId")String groupId);
	
	ResourceGroup getResourceGroupOnResourceIdAndGroupId(@Param(value="resourceId")String resourceId,@Param(value="groupId")String groupId);
	
	
}
