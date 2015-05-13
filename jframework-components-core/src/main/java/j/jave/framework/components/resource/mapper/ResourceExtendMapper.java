/**
 * 
 */
package j.jave.framework.components.resource.mapper;

import java.util.List;

import j.jave.framework.components.resource.model.ResourceExtend;
import j.jave.framework.model.support.JModelMapper;
import j.jave.framework.mybatis.JMapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="ResourceExtendMapper")
@JModelMapper(component="ResourceExtendMapper",name=ResourceExtend.class)
public interface ResourceExtendMapper extends JMapper<ResourceExtend> {

	void updateCached(@Param(value="id")String id,@Param(value="cached")String cached);
	
	ResourceExtend getResourceExtendOnResourceId(@Param(value="resourceId")String resourceId);
	
	List<ResourceExtend> getAllResourceExtends();
}
