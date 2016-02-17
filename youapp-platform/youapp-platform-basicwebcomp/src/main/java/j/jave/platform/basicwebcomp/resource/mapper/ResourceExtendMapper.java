/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.mapper;

import j.jave.kernal.jave.model.support.JModelMapper;
import j.jave.platform.basicwebcomp.resource.model.ResourceExtend;
import j.jave.platform.basicwebcomp.resource.repo.ResourceExtendRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceExtendMapper")
@JModelMapper(component="ResourceExtendMapper",name=ResourceExtend.class)
public interface ResourceExtendMapper extends JMapper<ResourceExtend>,ResourceExtendRepo<JMapper<ResourceExtend>> {

	void updateCached(@Param(value="id")String id,@Param(value="cached")String cached);
	
	ResourceExtend getResourceExtendOnResourceId(@Param(value="resourceId")String resourceId);
	
	List<ResourceExtend> getAllResourceExtends();
}
