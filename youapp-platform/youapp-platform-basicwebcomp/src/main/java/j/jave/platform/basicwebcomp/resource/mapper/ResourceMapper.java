/**
 * 
 */
package j.jave.platform.basicwebcomp.resource.mapper;

import j.jave.kernal.jave.model.support.JModelRepo;
import j.jave.platform.basicwebcomp.resource.model.Resource;
import j.jave.platform.basicwebcomp.resource.repo.ResourceRepo;
import j.jave.platform.mybatis.JMapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

/**
 * @author J
 *
 */
@Component(value="resourceMapper.mapper")
@JModelRepo(component="resourceMapper.mapper",name=Resource.class)
public interface ResourceMapper extends JMapper<Resource> ,ResourceRepo<JMapper<Resource>> {
	
	List<Resource> getResources();
	
	Resource getResourceByURL(@Param(value="url")String url);
	
}
